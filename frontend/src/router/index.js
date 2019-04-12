import Vue from 'vue';
import Router from 'vue-router';
import BuscarAluno from '@/components/BuscarAluno';
import NovoAluno from '@/components/NovoAluno';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/alunos',
      name: 'BuscarAluno',
      component: BuscarAluno,
    },
    {
      path: '/alunos/novo',
      name: 'NovoAluno',
      component: NovoAluno,
    },
  ],
});
