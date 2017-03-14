var loopback = require('loopback');
var path = require('path');
var app = loopback();
app.set('legacyExplorer', false);

app.dataSource('Memory', {
  connector: loopback.Memory,
  defaultForType: 'db'
});

app.model(loopback.AccessToken);

var Client = app.model('Client', {
  options: {
    base: 'User',
    relations: {
      accessTokens: {
        model: "AccessToken",
        type: "hasMany",
        foreignKey: "userId"
      }
    }
  },
  dataSource: 'Memory'
});

Client.destroyAll(function () {
  Client.create({
    email: 'test@mail.com',
    password: 'test',
  });
});

app.dataSource('mail', { connector: 'mail', defaultForType: 'mail' });
loopback.autoAttach();

app.enableAuth();
app.use(loopback.token({ model: app.models.AccessToken }));
app.use(loopback.rest());
app.listen(3000, function() {
  console.log('https server is ready at https://localhost:3000.');
});