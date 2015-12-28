(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionsCtrl', function ($http) {
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
  });

})();