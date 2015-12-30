describe('Filter ewDate', function(){
  var filterToTest;

  beforeEach(module('ew-structure'));

  beforeEach(inject(function($filter){
    filterToTest = $filter('ewDate');
  }));

  it('should return ""  when no arg', function(){
    expect(filterToTest()).toBe('');
  });

  it('should return "28/12/2015" when date is "[2015,12,28,15,57,42]', function(){
    expect(filterToTest([2015,12,28,15,57,42])).toBe('28/12/2015');
  });

  it('should return "03/01/2015" when date is "[2015,1,3,15,57,42]', function(){
    expect(filterToTest([2015,1,3,15,57,42])).toBe('03/01/2015');
  });
});