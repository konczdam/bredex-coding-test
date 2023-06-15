<script setup>
import { useLayout } from '@/layout/composables/layout';
import { ref, computed } from 'vue';
import { useAuthStore } from '@/stores/AuthStore';
import router from '@/router';

const { contextPath } = useLayout();
const { login } = useAuthStore();
const username = ref('');
const password = ref('');

const logoUrl = computed(() => {
    return `${contextPath}layout/images/car-of-formula-1.svg`;
});
const loginAction = async () => {
    const success = await login({
        username: username.value,
        password: password.value
    });
    if (success) {
        router.push({ path: '/' });
    }
};
</script>

<template>
    <div
        class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
    >
        <div class="flex flex-column align-items-center justify-content-center">
            <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
            <div
                style="
                    border-radius: 56px;
                    padding: 0.3rem;
                    background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%);
                "
            >
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div>
                        <label for="email1" class="block text-900 text-xl font-medium mb-2">Username</label>
                        <InputText
                            id="email1"
                            type="text"
                            placeholder="Username"
                            class="w-full md:w-30rem mb-5"
                            style="padding: 1rem"
                            v-model="username"
                        />

                        <label for="password1" class="block text-900 font-medium text-xl mb-2">Password</label>
                        <Password
                            id="password1"
                            v-model="password"
                            placeholder="Password"
                            :toggleMask="true"
                            :feedback="false"
                            class="w-full mb-3"
                            inputClass="w-full"
                            inputStyle="padding:1rem"
                        ></Password>

                        <Button label="Sign In" class="w-full p-3 text-xl" @click="loginAction" :disabled='password?.length < 1 || username?.length < 1'></Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Toast></Toast>
</template>

<style scoped>
.pi-eye {
    transform: scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform: scale(1.6);
    margin-right: 1rem;
}
</style>
