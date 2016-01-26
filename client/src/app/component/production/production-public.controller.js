(function () {

  'use strict';

  angular.module('ew-admin').controller('ProductionPublicCtrl', function ($http, $state) {
    'ngInject';

    var ctrl = this;

    ctrl.categoryText = !!$state.params.id;

    $http
      .get('/api/public/production/' + $state.params.category + '/' + ($state.params.id ? $state.params.id : 'general'))
      .then(function (response) {
        ctrl.selected = response.data;
      });

    ctrl.seeOtherProduction = function(){
      $http
        .get('/api/public/production/random/general')
        .then(function (response) {
          ctrl.selected = response.data;
        });
    };

  });

})();