(function () {

  'use strict';

  angular.module('ew-admin').controller('MyProductionsCtrl', function ($http, ProductionService) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/secured/production/myself')
        .then(function(response){
          ctrl.productions = response.data;
        });
    }

    refresh();

    ctrl.changePage = function(page){
      console.log(page);
    }

    ctrl.update = ProductionService.updateProduction;
    ctrl.delete = function(production){
      ProductionService.deleteProduction(production, refresh);
    }

  });

})();