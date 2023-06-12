import { reactive } from 'vue';
import { defineStore } from 'pinia';
import axios, { HttpStatusCode } from 'axios';
import { useToast } from 'primevue/usetoast';

export const useAuthStore = defineStore('authStore', () => {
    const state = reactive({ user: {} });
    const toast = useToast();

    axios.defaults.baseURL = 'http://localhost:8080';

    async function login(loginData) {
        try {
            const response = await axios.post('/auth/signin', loginData);
            Object.assign(state.user, response.data);
            return true;
        } catch (e) {
            console.error(e);
            if (e.response.status === HttpStatusCode.Unauthorized) {
                toast.add({
                    severity: 'error',
                    summary: 'Wrong Password or Username',
                    life: 3000
                });
            } else {
                toast.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'There was an error. Please try again',
                    life: 3000
                });
            }
            return false;
        }
    }

    function logout() {
        Object.keys(state.user).forEach((key) => delete state.user[key]);
    }

    return { user: state.user, login, logout };
});
