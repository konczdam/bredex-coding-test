<script setup>
import { useLayout } from '@/layout/composables/layout';
import { computed } from 'vue';
import { useAuthStore } from '@/stores/AuthStore';
import Crud from '@/views/pages/Crud.vue';
import router from '@/router';

const { contextPath } = useLayout();

const logoUrl = computed(() => {
    return `${contextPath}layout/images/car-of-formula-1.svg`;
});
const { user, logout } = useAuthStore();
const userLoggedIn = computed(() => !!user?.token);

const navigateToLogin = () => {
    router.push({ path: '/auth/login' });
};

const navigateToProfile = () => {
    router.push({ path: '/profile' });
};
</script>

<template>
    <div class="surface-0 flex justify-content-center">
        <div id="home" class="landing-wrapper overflow-hidden">
            <div
                class="py-4 px-4 mx-0 md:mx-6 lg:mx-8 lg:px-8 flex align-items-center justify-content-between relative lg:static mb-3"
            >
                <a class="flex align-items-center" href="#">
                    <img :src="logoUrl" alt="Formula1 Logo" height="50" class="mr-0 lg:mr-2" /><span
                        class="text-900 font-medium text-2xl line-height-3 mr-8"
                        >Formula-1</span
                    >
                </a>
                <a
                    class="cursor-pointer block lg:hidden text-700 p-ripple"
                    v-ripple
                    v-styleclass="{
                        selector: '@next',
                        enterClass: 'hidden',
                        leaveToClass: 'hidden',
                        hideOnOutsideClick: true
                    }"
                >
                    <i class="pi pi-bars text-4xl"></i>
                </a>
                <div
                    class="align-items-center surface-0 flex-grow-1 justify-content-end hidden lg:flex absolute lg:static w-full left-0 px-6 lg:px-0 z-2"
                    style="top: 120px"
                >
                    <div
                        class="flex justify-content-between lg:block border-top-1 lg:border-top-none surface-border py-3 lg:py-0 mt-3 lg:mt-0"
                    >
                        <Button
                            v-if="userLoggedIn"
                            label="Profile"
                            class="p-button-rounded border-none ml-5 font-light text-white line-height-2 bg-blue-500"
                            @click="navigateToProfile"
                        ></Button>
                        <Button
                            v-if="!userLoggedIn"
                            label="Login"
                            class="p-button-rounded border-none ml-5 font-light text-white line-height-2 bg-blue-500"
                            @click="navigateToLogin"
                        ></Button>
                        <Button
                            v-else
                            label="Logout"
                            class="p-button-rounded border-none ml-5 font-light text-white line-height-2 bg-red-500"
                            @click="logout"
                        ></Button>
                    </div>
                </div>
            </div>

            <Crud />
        </div>
    </div>
</template>
