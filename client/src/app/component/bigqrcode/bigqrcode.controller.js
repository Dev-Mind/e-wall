(function () {

  'use strict';

  angular.module('ew-admin').controller('BigQRCodeCtrl', function ($http, $timeout, $window, $uibModal, qrCodeParameters) {
    'ngInject';

    var ctrl = this;

    //We read the user preference
    var json = $window.localStorage.getItem('parameters');
    if(!json){
      ctrl.parameterMap = qrCodeParameters;
    }
    else{
      ctrl.parameterMap = angular.fromJson(json);
    }

    function refresh(){
      $http
        .get('/api/public/category')
        .then(function(response){
          ctrl.categories = response.data;
          if(ctrl.categories && ctrl.categories.length>0){
            ctrl.selectCategory(ctrl.categories[0].id);
          }
        });
    }

    ctrl.selectCategory = function(id){
      $http.get('/api/public/category/' + id)
        .then(function (response) {
          ctrl.qrs = response.data.qrcodes
            .filter(function(elt){
              return elt.big!==true;
            });

          var mainQr = response.data.qrcodes
            .filter(function(elt){
              return elt.big===true;
            })[0];

          $timeout(function(){
            var svg = document.querySelector('.bigqrcode');

            //The svg dimension is computed with the window size
            var h  = (window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight)*0.83;
            var w  = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth)*0.83;
            var dimension = h > w ? w : h;
            svg.style.height = dimension;
            svg.style.width = dimension;

            var ratio = dimension/mainQr.dimension;

            ctrl.text = {
              size : mainQr.dimension/2 * ratio + 'px',
              x:dimension/3,
              y:dimension - dimension/5,
              content:response.data.shortCode
            };

            ctrl.qrs.forEach(function(elt){
              elt.background = {
                x : elt.x*ratio,
                y :elt.y*ratio,
                dimensionScaled : ratio
              };
              elt.foreground = {
                x : elt.x*ratio + ctrl.parameterMap.qrcode_margin,
                y :elt.y*ratio + ctrl.parameterMap.qrcode_margin,
                dimensionScaled : ratio - 2 *ctrl.parameterMap.qrcode_margin
              };
              if(elt.foreground.dimensionScaled<0){
                elt.foreground.dimensionScaled = 1;
              }
              elt.scaleRatio = ratio/(elt.dimension);

            });
          });

        });
    };

    ctrl.seeProduction = function(qr){
      if(!!qr.production) {
        var modalInstance = $uibModal.open({
          animation: true,
          templateUrl: 'seeProduction.html',
          controller: 'SeeProductionCtrl',
          resolve: {
            production: function () {
              return $http.get('/api/public/qrcode/'+qr.production.id).then(function(response){
                return response.data.production;
              });
            }
          }
        });
      }
    };

    refresh();
  });


})();