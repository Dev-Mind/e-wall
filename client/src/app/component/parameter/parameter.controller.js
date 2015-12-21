(function () {

  'use strict';

  angular.module('ew-admin').controller('ParameterCtrl', function ($window, $state, qrCodeParameters) {
    'ngInject';

    var ctrl = this;

    function refresh(){
      var json = $window.localStorage.getItem('parameters');
      if(!json){
        ctrl.parameterMap = qrCodeParameters;
      }
      else{
        ctrl.parameterMap = angular.fromJson(json);
      }
    }

    ctrl.save = function(){
      $window.localStorage.setItem('parameters', angular.toJson(ctrl.parameterMap));
    };

    ctrl.default = function(){
      $window.localStorage.setItem('parameters', angular.toJson(qrCodeParameters));
      refresh();
    };

    refresh();
  });

})();