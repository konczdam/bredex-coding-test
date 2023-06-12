import { reactive } from "vue";
import { defineStore } from "pinia";
import axios, {HttpStatusCode} from "axios";
import { useAuthStore} from "@/stores/AuthStore";
import { useToast } from "primevue/usetoast";

export const useFormula1TeamStore = defineStore("formula1TeamStore", () =>{
    const state = reactive({teams: []})
    const toast = useToast();

    axios.defaults.baseURL = 'http://localhost:8080';

    async function fetchTeams() {
        try {
            const response = await axios.get('/teams');
            state.teams.length = 0;
            state.teams.push(...response.data);
        } catch (e) {
            console.error(e);
            toast.add({
                severity: 'error',
                summary: 'Error',
                detail: 'There was an error. Please try again',
                life: 3000
            });
            return false;
        }
    }

    return { teams: state.teams, fetchTeams };
});
