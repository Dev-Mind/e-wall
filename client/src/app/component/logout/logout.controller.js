(function () {

  'use strict';

  angular.module('ew-structure').controller('LogoutCtrl', function ($http, $state) {
    'ngInject';

    $http
      .get('api/public/logout')
      .then(function () {
        $state.go('home');
      });

  });

})();