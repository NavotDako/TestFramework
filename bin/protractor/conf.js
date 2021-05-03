exports.config = {
    framework: 'jasmine',
    protocol: 'https',
    seleniumAddress: 'https://qa-win2016.experitest.com/wd/hub',
    
    specs: ['spec.js'],
    capabilities: {
        accessKey: 'eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVME9UZzNOVGd4TnpVMU9BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjUyMzU4MTgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.z0JrY0SIIeQtkCAPQBFOBoLQ8v9zTfR7tEKI_Jk4O8s',
        browserName: 'chrome',
        testName: 'navot',
        platformName: 'android',
        //app: "cloud:be.kbc.TouchFet.debug/com.kbc.touch.Touch",
        deviceQuery: "@os='android' and @serialnumber='FA77L0301164'",
      //  udid: 'NNF6R18B02000738',
        autoWebview: true,
        autoWebviewTimeout: 20000,
        autoAcceptAlerts: true,
        autoGrantPermissions: true,
        newCommandTimeout: 300000,
        instrumentApp: true
    }
    
  }