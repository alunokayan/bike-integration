const { createApp } = Vue;
const baseUrl = window.location.origin + "/bike-integration";

createApp({
  data() {
    return {
      loginData: {
        accessKey: '',
        secretKey: ''
      },
      loginError: '',
      loginExpired: false
    };
  },
  mounted() {
    const params = new URLSearchParams(window.location.search);
    if (params.get('expired') === 'true') {
      this.loginExpired = true;
    }
  },
  methods: {
    async login() {
      this.loginError = '';
      try {
        const response = await fetch(`${baseUrl}/app/login?username=${this.loginData.accessKey}&password=${this.loginData.secretKey}`, {
          method: 'POST'
        });
        if (response.ok) {
          const data = await response.json();
          const token = data.token;
          const userId = data.userId;
          const userName = data.userName;

          setCookie('token', token, 1);
          setCookie('userId', userId, 1);
          setCookie('userName', userName, 1);
          window.location.href = 'home';
        } else {
          this.loginError = 'Erro ao fazer login.';
        }
      } catch (error) {
        this.loginError = 'Erro na requisição de login.';
        console.error(error);
      }
    }
  }
}).mount("#app");