describe('Protractor Demo App', function() {
  it('should have a title', function() {
	  browser.get('https://angularjs.org/');
	  expect(browser.getCurrentUrl()).toBe('https://angularjs.org/');
   elementLanguage = element.all(By.css(".atlas-logon-language"));
   elementLanguage.click();
    
  });
});