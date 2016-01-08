(function () {

  'use strict';

  angular.module('ew-admin').controller('WallCtrl', function ($http, $state) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      $http
        .get('/api/public/category/with-qrcode')
        .then(function(response){
          ctrl.categories = response.data;
        });
    }

    ctrl.goBigQrCode = function(category){
      $state.go('bigqrcode', {id : category.id});
    };

    refresh();
  });

})();