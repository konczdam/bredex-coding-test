<script setup>
import { useToast } from 'primevue/usetoast';
import { useAuthStore } from '@/stores/AuthStore';

// eslint-disable-next-line no-undef
const { user } = useAuthStore();
const toast = useToast();
async function clipboardCopy(field, successMessage) {
    if (field) {
        try {
            await navigator.clipboard.writeText(field);
            toast.add({
                severity: 'success',
                summary: 'Copied',
                detail: successMessage,
                life: 3000
            });
        } catch (err) {
            toast.add({
                severity: 'warning',
                summary: 'Copying failed',
                detail: `Failed to copy: ${err}`,
                life: 3000
            });
        }
    }
}
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <div class="p-d-flex p-flex-column p-ai-center">
                    <h2>{{ user.username }}</h2>
                    <p>{{ user.email }}</p>
                    <div class="p-d-flex p-flex-column p-ai-center">
                        <h3>Roles</h3>
                        <ul>
                            <li v-for="(role, index) in user.roles" :key="index">
                                {{ role }}
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="dev-section">
                    <h3>Fejlesztőknek</h3>
                    <div class="p-inputgroup p-mt-3 mt-3">
                        <InputText id="access-token" readonly :value="user.token" />
                        <Button
                            icon="pi pi-copy"
                            class="p-buttons"
                            @click="clipboardCopy(user.token, 'Access token copied to clipboard')"
                        />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Toast />
</template>

<style scoped>
.dev-section {
    margin-top: 2rem;
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
}
</style>
