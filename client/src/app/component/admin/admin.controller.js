(function () {

  'use strict';

  angular.module('ew-admin').controller('AdminCtrl', function ($http, SecurityService) {
    'ngInject';

    var ctrl = this;

    $http.get('api/secured/role')
      .then(function(response) {
        console.log('OK', response.data)
        ctrl.isAdmin = SecurityService.isAuthorized('ADMIN', response.data);
      })
      .catch(function(response) {
        ctrl.isAdmin = false;
      });

  });

})();