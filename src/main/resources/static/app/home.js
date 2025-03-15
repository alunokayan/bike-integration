const { createApp } = Vue;
const baseUrl = window.location.origin + "/bike-integration";

document.addEventListener('DOMContentLoaded', () => {
    createApp({
        data() {
            return {
                events: [],
                isLogged: false,
                eventError: ''
            };
        },
        mounted() {
            this.loadHome();
        },
        methods: {
            async loadHome() {
                const token = getCookie('token');
                if (token) {
                  fetch(`${baseUrl}/v1/auth`, {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}`}
                  })
                  .then(response => {
                    if (response.status === 204) {
                      this.isLogged = true;
                    } else {
                      this.isLogged = false;
                    }
                  })
                  .catch(error => {
                    localStorage.removeItem('token');
                    this.isLogged = false;
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