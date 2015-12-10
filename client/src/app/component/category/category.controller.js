(function () {

  'use strict';

  angular.module('nw-admin').controller('CategoryCtrl', function ($http) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/category')
        .then(function(response){
          ctrl.categories = response.data;
        });
    }

    ctrl.entity = {};
    refresh();
  });

})();