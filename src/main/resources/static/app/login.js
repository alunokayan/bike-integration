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
      const formData = new URLSearchParams();
      formData.append('accessKey', this.loginData.accessKey);
      formData.append('secretKey', this.loginData.secretKey);
      try {
        const response = await fetch(`${baseUrl}/v1/auth`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: formData
        });
        if (response.ok) {
          const token = await response.json().then(data => data.token);
          setCookie('token', token, 1);
          window.location.href = 'home';
        } else {
          this.loginError = 'Erro ao fazer login.';
        }
      } catch (error) {
        this.loginError = 'Erro na requisição de login.';
      }
    }
  }
}).mount("#app");