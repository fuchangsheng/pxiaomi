'use strict';

var options = {
	name: 'tom',
	age: 20,
	sex: 'male'
};

var test = 'name' in options;
console.log(test);
console.log('132' in options);