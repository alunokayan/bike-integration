const { createApp } = Vue;
const baseUrl = window.location.origin + "/bike-integration";

document.addEventListener('DOMContentLoaded', () => {
    createApp({
        data() {
            return {
                events: [],
                isLogged: false,
                isAdm: false,
                eventError: ''
            };
        },
        mounted() {
            this.loadHome();
        },
        methods: {
            async loadHome() {
                this.isLogged = false;
                const token = getCookie('token');
                if (token) {
                  fetch(`${baseUrl}/app/isUsuarioValido`, {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}`}
                  })
                  .then(response => {
                    if (response.status === 204) {
                      this.isLogged = true;
                      this.isAdm = true;
                    } else if (response.status === 403) {
                      this.isLogged = true;
                      this.eventError = 'Usuário logado não tem acesso a aplicação.';
                    }
                  })
                  .catch(error => {
                    this.eventError = 'Erro na requisição de login.';
                  });
                }
              },
            logout() {
                setCookie('token', null, -1);
                window.location.href = 'home';
            }
        }
    }).mount("#app");
});