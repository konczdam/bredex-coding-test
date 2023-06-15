import { reactive } from 'vue';
import { defineStore } from 'pinia';
import axios, { HttpStatusCode } from 'axios';
import { useToast } from 'primevue/usetoast';
import { useAuthStore } from '@/stores/AuthStore';

export const useFormula1TeamStore = defineStore('formula1TeamStore', () => {
    const state = reactive({ teams: [] });
    const toast = useToast();
    const { user } = useAuthStore();

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

    async function saveTeam(team) {
        if (team.yearFounded instanceof Date) {
            team.yearFounded = team.yearFounded.getFullYear();
        }
        if (team.id) {
            return _updateTeam(team);
        } else {
            return _saveNewTeam(team);
        }
    }

    async function deleteTeam(team) {
        try {
            const response = await axios.delete(`/teams/${team.id}`, {
                headers: {
                    Authorization: `Bearer ${user.token}`
                }
            });
            if (response.status !== HttpStatusCode.NoContent) {
                toast.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Error while deleting the team',
                    life: 3000
                });
            } else {
                toast.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'The team was successfully deleted',
                    life: 3000
                });
            }
            return response.status;
        } catch (e) {
            console.error(e);
            toast.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error while deleting the team',
                life: 3000
            });
        }
    }

    async function _updateTeam(team) {
        try {
            const response = await axios.put(
                `/teams/${team.id}`,
                { ...team },
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`
                    }
                }
            );
            if (response.status !== HttpStatusCode.Ok) {
                toast.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Error updating the team',
                    life: 3000
                });
            } else {
                toast.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'The team was successfully updated',
                    life: 3000
                });
            }
            return response.status;
        } catch (e) {
            console.error(e);
            toast.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error updating the team',
                life: 3000
            });
        }
    }

    async function _saveNewTeam(team) {
        try {
            const response = await axios.post(
                '/teams',
                { ...team },
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`
                    }
                }
            );
            if (response.status !== HttpStatusCode.Created) {
                toast.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Error creating the team',
                    life: 3000
                });
            } else {
                toast.add({
                    severity: 'success',
                    summary: 'Success',
                    detail: 'The team was successfully created',
                    life: 3000
                });
            }
            return response.status;
        } catch (e) {
            console.error(e);
            toast.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error creating the team',
                life: 3000
            });
        }
    }

    return { teams: state.teams, fetchTeams, saveTeam, deleteTeam };
});
