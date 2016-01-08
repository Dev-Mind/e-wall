(function () {

  'use strict';

  angular.module('ew-admin').controller('MainCtrl', function ($http, SecurityService) {
    'ngInject';

    var ctrl = this;

    SecurityService.isAdmin(function(response){
      ctrl.isAdmin = response;
    });

  });

})();