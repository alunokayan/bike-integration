const { createApp } = Vue;
const baseUrl = window.location.origin + "/bike-integration";

createApp({
  data() {
    return {
      events: [],
      eventError: '',
      currentPage: 1,
      totalPages: 0,
      totalEvents: 1,
      filters: {
        nome: '',
        descricao: '',
        data: '',
        cidade: '',
        estado: '',
        faixaKm: '',
        tipoEvento: '',
        nivelHabilidade: '',
        gratuito: '',
        urlSite: '',
        aprovado: ''
      },
      newEvent: {
        nome: '',
        descricao: '',
        data: '',
        endereco: {
          cep: '',
          estado: '',
          cidade: '',
          bairro: '',
          rua: '',
          numero: ''
        },
        faixaKm: '',
        gratuito: false,
        urlSite: '',
        idTipoEvento: '',
        idUsuario: ''
      },
      tiposEvento: [],
      niveisHabilidade: []
    };
  },
  mounted() {
    this.hasAccess();
    this.loadEvents();
    this.loadNivelHabilidade();
    this.loadTipoEvento();
  },
  methods: {
    hasAccess() {
      const token = getCookie('token');
      if (token) {
        fetch(`${baseUrl}/app/isUsuarioValido`, {
          method: 'GET',
          headers: { 'Authorization': `Bearer ${token}` }
        })
          .then(response => {
            if (response.status === 204) {
              // usuário logado e válido
            } else if (response.status === 403) {
              setCookie('token', null, -1);
              window.location.href = 'login?expired=true';
            }
          })
          .catch(error => {
            console.error(error);
            this.eventError = 'Erro na requisição de login.';
          });
      } else {
        window.location.href = 'login';
      }
    },
    async loadEvents() {
      const token = getCookie('token');
      const params = new URLSearchParams();
      params.append('pagina', this.currentPage);
      
      // Adiciona os filtros, se preenchidos
      if (this.filters.nome) params.append('nome', this.filters.nome);
      if (this.filters.descricao) params.append('descricao', this.filters.descricao);
      if (this.filters.data) params.append('data', this.filters.data);
      if (this.filters.cidade) params.append('cidade', this.filters.cidade);
      if (this.filters.estado) params.append('estado', this.filters.estado);
      if (this.filters.faixaKm) params.append('faixaKm', this.filters.faixaKm);
      if (this.filters.tipoEvento) params.append('tipoEvento', this.filters.tipoEvento);
      if (this.filters.nivelHabilidade) params.append('nivelHabilidade', this.filters.nivelHabilidade);
      if (this.filters.gratuito) params.append('gratuito', this.filters.gratuito);
      if (this.filters.aprovado) params.append('aprovado', this.filters.aprovado);
      
      try {
        const response = await fetch(`${baseUrl}/v1/evento/?${params.toString()}`, {
          method: 'GET',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          const data = await response.json();
          this.events = data.eventos;
          this.totalPages = data.totalPaginas;
          this.totalEvents = data.totalRegistros;
        } else {
          this.eventError = 'Erro ao carregar eventos.';
        }
      } catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para carregar eventos.';
      }
    },
    async loadNivelHabilidade() {
      const token = getCookie('token');
      try {
        const response = await fetch(`${baseUrl}/v1/nivel/habilidade/`, {
          method: 'GET',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          this.niveisHabilidade = await response.json();
        } else {
          this.eventError = 'Erro ao carregar níveis de habilidade.';
        }
      } catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para carregar níveis de habilidade.';
      }
    },
    async loadTipoEvento() {
      const token = getCookie('token');
      try {
        const response = await fetch(`${baseUrl}/v1/tipo/evento/`, {
          method: 'GET',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          this.tiposEvento = await response.json();
        } else {
          this.eventError = 'Erro ao carregar tipos de evento.';
        }
      }
      catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para carregar tipos de evento.';
      }

    },
    async aprovarEvento(id) {
      const token = getCookie('token');
      try {
        const response = await fetch(`${baseUrl}/v1/evento/${id}/${true}`, {
          method: 'PUT',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          this.loadEvents();
        } else {
          this.eventError = 'Erro ao aprovar evento.';
        }
      } catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para aprovar evento.';
      }
    },
    async reprovarEvento(id) {
      const token = getCookie('token');
      try {
        const response = await fetch(`${baseUrl}/v1/evento/${id}/${false}`, {
          method: 'PUT',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          this.loadEvents();
        } else {
          this.eventError = 'Erro ao reprovar evento.';
        }
      } catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para reprovar evento.';
      }
    },
    async deleteEvento(id) {
      const token = getCookie('token');
      try {
        const response = await fetch(`${baseUrl}/v1/evento/${id}`, {
          method: 'DELETE',
          headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.status === 403) {
          setCookie('token', null, -1);
          window.location.href = 'login?expired=true';
        }
        if (response.ok) {
          this.loadEvents();
        } else {
          this.eventError = 'Erro ao deletar evento.';
        }
      } catch (error) {
        console.error(error);
        this.eventError = 'Erro na requisição para deletar evento.';
      }
    },
    logout() {
      setCookie('token', null, -1);
      window.location.href = 'home';
    },
    goToCreateEvent() {
      const modalElement = document.getElementById('createEventModal');
      const modalInstance = new bootstrap.Modal(modalElement);
      modalInstance.show();
    },
    submitEvent() {
      const token = getCookie('token');
      fetch(`${baseUrl}/v1/evento/`, {
          method: 'POST',
          headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.newEvent)
      })
      .then(response => {
          if (response.ok) {
              this.loadEvents();
              const modalElement = document.getElementById('createEventModal');
              const modalInstance = bootstrap.Modal.getInstance(modalElement);
              modalInstance.hide();
          } else {
              this.eventError = 'Erro ao criar evento.';
          }
      })
      .catch(error => {
          console.error(error);
          this.eventError = 'Erro na requisição para criar evento.';
      });
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.loadEvents();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.loadEvents();
      }
    },
    applyFilters() {
      this.currentPage = 1;
      this.loadEvents();
    }
  }
}).mount("#app");