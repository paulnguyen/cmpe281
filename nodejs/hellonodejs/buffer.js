

var b = new Buffer(10000);

var s1 = "Hello World";
b.write(s1);
console.log(b.length);
console.log(s1.length);

var s2 = "我叫王马克";
b.write(s2);
console.log(s2.length);
console.log(Buffer.byteLength(s2));



