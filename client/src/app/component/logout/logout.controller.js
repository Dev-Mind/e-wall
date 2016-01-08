(function () {

  'use strict';

  angular.module('ew-structure').controller('LogoutCtrl', function ($http, $state, SecurityService) {
    'ngInject';

    $http
      .get('api/public/logout')
      .then(function () {
        $state.go('home');
      });

    SecurityService.isAdmin(function(response){
      ctrl.isAdmin = response;
    });

  });

})();