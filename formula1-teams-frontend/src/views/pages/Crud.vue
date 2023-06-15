<script setup>
import { cloneDeep } from 'lodash';
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount, computed } from 'vue';
import { useAuthStore } from '@/stores/AuthStore';
import { useFormula1TeamStore } from '@/stores/Formula1TeamStore';
import { HttpStatusCode } from 'axios';

const formData = ref({});
const loading = ref(false);
const teamDialog = ref(false);
const deleteTeamDialog = ref(false);
const team = ref({});
const dt = ref(null);
const filters = ref({});
const form = ref(null);

const { user } = useAuthStore();
const userLoggedIn = computed(() => !!user?.token);
const { teams, fetchTeams, saveTeam, deleteTeam } = useFormula1TeamStore();

onBeforeMount(() => {
    initFilters();
});
onMounted(() => {
    loading.value = true;
    fetchTeams().then(() => (loading.value = false));
});
const openNew = () => {
    team.value = {};
    formData.value = {};
    teamDialog.value = true;
};

const hideDialog = () => {
    teamDialog.value = false;
};

const callSaveTeam = async () => {
    const validationResult = await form.value.validate();
    if (!validationResult.valid) {
        return;
    }
    loading.value = true;
    const result = await saveTeam(cloneDeep(formData.value));
    if (result === HttpStatusCode.Ok || result === HttpStatusCode.Created) {
        teamDialog.value = false;
        loading.value = true;
        await fetchTeams();
        loading.value = false;

        team.value = {};
        formData.value = {};
    }
    loading.value = false;
};

const editTeam = (editTeam) => {
    formData.value = cloneDeep(editTeam);
    teamDialog.value = true;
};

const confirmDeleteTeam = (editProduct) => {
    team.value = editProduct;
    deleteTeamDialog.value = true;
};

const callDeleteTeam = async () => {
    const result = await deleteTeam(team.value);
    deleteTeamDialog.value = false;
    team.value = {};
    if (result === HttpStatusCode.NoContent) {
        loading.value = true;
        await fetchTeams();
        loading.value = false;
    }
};

const initFilters = () => {
    filters.value = {
        name: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toast />
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button
                                label="New"
                                icon="pi pi-plus"
                                class="p-button-success mr-2"
                                @click="openNew"
                                :disabled="!userLoggedIn"
                            />
                        </div>
                    </template>
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="teams"
                    dataKey="id"
                    class="p-datatable-gridlines"
                    :loading="loading"
                    :paginator="true"
                    :rows="10"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
                    responsiveLayout="scroll"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">Manage Teams</h5>
                            <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['name'].value" placeholder="Search..." />
                            </span>
                        </div>
                    </template>

                    <Column field="name" header="Name" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Name</span>
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column
                        field="yearFounded"
                        header="Year founded"
                        :sortable="true"
                        headerStyle="width:14%; min-width:10rem;"
                    >
                        <template #body="slotProps">
                            <span class="p-column-title">Year founded</span>
                            {{ slotProps.data.yearFounded }}
                        </template>
                    </Column>
                    <Column
                        field="championshipsWon"
                        header="ChampionshipsWon"
                        :sortable="true"
                        headerStyle="width:14%; min-width:10rem;"
                    >
                        <template #body="slotProps">
                            <span class="p-column-title">championshipsWon</span>
                            {{ slotProps.data.championshipsWon }}
                        </template>
                    </Column>
                    <Column
                        field="entryFeePaid"
                        header="Entry Fee Paid"
                        :sortable="true"
                        data-type="boolean"
                        :style="{ width: '150px' }"
                    >
                        <template #body="slotProps">
                            <i
                                style="font-size: 24px"
                                class="pi"
                                :class="{
                                    'pi-times-circle text-red-400': !slotProps.data.entryFeePaid,
                                    'pi-check-circle text-green-500': slotProps.data.entryFeePaid
                                }"
                            ></i>
                        </template>
                        <template #filter="{ filterModel }">
                            <Checkbox v-model="filterModel.entryFeePaid" />
                        </template>
                    </Column>
                    <Column headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <Button
                                icon="pi pi-pencil"
                                class="p-button-rounded p-button-success mr-2"
                                :disabled="!userLoggedIn"
                                @click="editTeam(slotProps.data)"
                            />
                            <Button
                                icon="pi pi-trash"
                                class="p-button-rounded p-button-warning mt-2"
                                :disabled="!userLoggedIn"
                                @click="confirmDeleteTeam(slotProps.data)"
                            />
                        </template>
                    </Column>
                </DataTable>

                <Dialog
                    v-model:visible="teamDialog"
                    :style="{ width: '450px' }"
                    header="Product Details"
                    :modal="true"
                    class="p-fluid"
                >
                    <Form @submit="callSaveTeam" ref="form">
                        <!-- NAME-->
                        <div class="field">
                            <label for="name">Name</label>
                            <div>
                                <Field name="name" rules="required|max:150" v-model="formData.name">
                                    <template v-slot="{ field, meta }">
                                        <InputText
                                            id="name"
                                            type="text"
                                            v-model="formData.name"
                                            v-bind="field"
                                            class="mb-1"
                                            :class="{ 'p-invalid': meta.touched && !meta.valid }"
                                        />
                                    </template>
                                </Field>
                                <ErrorMessage name="name" class="error-message" />
                            </div>
                        </div>
                        <!-- YEAR FOUNDED-->
                        <div class="field">
                            <label for="yearFounded">Year Founded</label>
                            <div>
                                <Field name="yearFounded" rules="required" v-model="formData.yearFounded">
                                    <template v-slot="{ field, meta }">
                                        <Calendar
                                            id="yearFounded"
                                            type="text"
                                            v-model="formData.yearFounded"
                                            v-bind="field"
                                            view="year"
                                            dateFormat="yy"
                                            class="mb-1"
                                            :class="{ 'p-invalid': meta.touched && !meta.valid }"
                                        />
                                    </template>
                                </Field>
                                <ErrorMessage name="yearFounded" class="error-message" />
                            </div>
                        </div>
                        <!-- CHAMPIONSHIPS WON-->
                        <div class="field">
                            <label for="championshipsWon">Championships won</label>
                            <div>
                                <Field
                                    name="championshipsWon"
                                    rules="required|min:0|numeric"
                                    v-model="formData.championshipsWon"
                                >
                                    <template v-slot="{ field, meta }">
                                        <InputText
                                            id="name"
                                            v-model="formData.championshipsWon"
                                            v-bind="field"
                                            class="mb-1"
                                            :class="{ 'p-invalid': meta.touched && !meta.valid }"
                                        />
                                    </template>
                                </Field>
                                <ErrorMessage name="championshipsWon" class="error-message" />
                            </div>
                        </div>

                        <!-- ENTRY FEE PAID-->
                        <div class="field">
                            <label for="entryFeePaid">Entry fee paid</label>
                            <div>
                                <Field name="entryFeePaid" v-slot="{ field }" :value="formData.entryFeePaid">
                                    <Checkbox
                                        id="entryFeePaid"
                                        v-model="formData.entryFeePaid"
                                        @update:modelValue="field.onChange"
                                        binary
                                    />
                                </Field>
                            </div>
                        </div>
                    </Form>
                    <template #footer>
                        <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
                        <Button label="Save" icon="pi pi-check" class="p-button-text" @click="callSaveTeam" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deleteTeamDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="team"
                            >Are you sure you want to delete <b>{{ team.name }}</b
                            >?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteTeamDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="callDeleteTeam" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
.field > .p-checkbox {
    display: block;
}

.error-message {
    color: #ff5757ff; /*TODO somehow reference $errorMessageTextColor; */
}
</style>
