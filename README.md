### Building
```
Can build by running the build.bat file.
```

### Web Server Path
```
/system_ex/app/NPXS40140/cdc/modules/www
```

### Jailbreak hosting
BD-J applications cause the power assigned to the web browser to drop, the only way to circumvent this situation is to run the following javascript code after all resources are loaded.
```javascript
const Http = new XMLHttpRequest();
const url='/bdj/exit';
Http.open("GET", url, false);
Http.send();
```