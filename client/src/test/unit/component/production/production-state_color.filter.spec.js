describe('Filter stateColor', function(){
  var filterToTest;

  beforeEach(module('ew-admin'));

  beforeEach(inject(function($filter){
    filterToTest = $filter('stateColor');
  }));

  it('should return ""  when no arg', function(){
    expect(filterToTest()).toBe('');
  });

  it('should return "label-default" when state is "PENDING', function(){
    expect(filterToTest('PENDING')).toBe('label-default');
  });

  it('should return "label-primary" when state is "VALIDATED', function(){
    expect(filterToTest('VALIDATED')).toBe('label-primary');
  });

  it('should return "label-danger" when state is "CENSORED', function(){
    expect(filterToTest('CENSORED')).toBe('label-danger');
  });


});