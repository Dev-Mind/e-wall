(function () {

  'use strict';

  angular.module('ew-admin').controller('AdminCtrl', function ($http, SecurityService) {
    'ngInject';

    var ctrl = this;

    SecurityService.isAdmin(function(response){
      ctrl.isAdmin = response;
    });

  });

})();