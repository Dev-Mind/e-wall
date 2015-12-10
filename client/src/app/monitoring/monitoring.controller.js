(function () {

  'use strict';

  angular.module('nw-admin').controller('MonitoringCtrl', function ($http) {
    'ngInject';

    var ctrl = this;

    ctrl.monitor = function(key){
      $http.get('/monitoring/' + key)
        .then(function (response) {
          ctrl.report = key;
          ctrl.result = response.data;
        });
    };

    ctrl.monitor('info');
  });

})();