import Vue from 'vue';
import Router from 'vue-router';
import BuscarAluno from '@/components/BuscarAluno';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/aluno/',
      name: 'BuscarAluno',
      component: BuscarAluno,
    },
  ],
});
