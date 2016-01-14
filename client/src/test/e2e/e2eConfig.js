//  Add e2e module to main pb module
angular.module('ewall').requires.push('ewall.e2e');

angular.module('ewall.e2e', ['ngMockE2E']).run(function ($httpBackend) {

    // I18n
    $httpBackend.whenGET(/i18n\/.*-fr-FR.json/).respond({'fr': {'New page': 'Nouvelle page'}});

    //Example
    $httpBackend.whenGET('app/test').respond(200);


  });
