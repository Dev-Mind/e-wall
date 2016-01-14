(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionsCtrl', function ($http, ProductionService, STATE) {
    'ngInject';

    var ctrl = this;
    ctrl.currentPage = 1;
    ctrl.search = {
      state : 'PENDING'
    };
    ctrl.states = STATE;

    ctrl.refresh = function(){
      console.log(ctrl.search.category ? ctrl.search.category.code : null)
      $http
        .put('/api/secured/production/' + (ctrl.currentPage - 1), {
          category : ctrl.search.category ? ctrl.search.category.code : null,
          state : ctrl.search.state,
          content: ctrl.search.content
        })
        .then(function(response){
          ctrl.productionsPage = response.data;
        });
    };

    $http
      .get('/api/public/category')
      .then(function (response) {
        ctrl.categories = response.data;
      });

    ctrl.refresh();
    ctrl.update = ProductionService.updateProduction;
    ctrl.seeProduction=

    ctrl.delete = function(production){
      ProductionService.deleteProduction(production, ctrl.refresh);
    };

    ctrl.seeProduction = function(production){
      ProductionService.seeProduction(production);
    };
  });

})();