"use strict";

describe('Directive login', function () {

  var $compile, $scope, $httpBackend, element, frm;

  beforeEach(module('ew-admin'));

  beforeEach(inject(function ($injector) {
    $scope = $injector.get('$rootScope').$new();
    $compile = $injector.get('$compile');
    $httpBackend = $injector.get('$httpBackend');

    $scope.credentials  = {
      id: 1
    };

    element = $compile('<form name="frm"><input type="text" ng-required="true" name="code" unique-category-code id="credentials.id" ng-model="credentials.code"/></form>')($scope);

    $scope.$digest();
    frm = $scope.frm;
  }));

  it('should be invalid when empty because value is required', function () {
    expect(frm.code.$error.required).toBeTruthy();
  });

  it('should be invalid when code already exists', function () {
    $httpBackend.expectGET('/api/category/check/test?id=1').respond(409, '');

    $scope.credentials.code = "test";
    $scope.$digest();
    $httpBackend.flush();
    expect(frm.code.$error.uniqueCategoryCode).toBeTruthy();
  });

  it('should be invalid when code already exists and we are in creation', function () {
    $httpBackend.expectGET('/api/category/check/test').respond(409, '');

    delete $scope.credentials.id;
    $scope.credentials.code = "test";
    $scope.$digest();
    $httpBackend.flush();
    expect(frm.code.$error.uniqueCategoryCode).toBeTruthy();
  });

  it('should be valid when login not exists', function () {
    $httpBackend.expectGET('/api/category/check/test?id=1').respond(200);

    $scope.credentials.code = "test";
    $scope.$digest();
    $httpBackend.flush();
    expect(frm.code.$valid).toBeTruthy();
  });

});