# Changelog

## [1.10.0](https://github.com/teletha/reincarnation/compare/v1.9.0...v1.10.0) (2024-12-09)


### Features

* chained assignment outputs clean code ([3d34be9](https://github.com/teletha/reincarnation/commit/3d34be99007dfdaee7f83a7d578893bd9094a914))
* drop LocalVariables#register ([2df283a](https://github.com/teletha/reincarnation/commit/2df283a5ddb91dec2c3a117301d4773d41eecb56))
* provide Low-Line-Verification system in test case ([ad05b8e](https://github.com/teletha/reincarnation/commit/ad05b8eaa7502da3f92fb13ee927961618f28d78))
* special handling on MethodHandle#invoke and #invokeExact ([d88be08](https://github.com/teletha/reincarnation/commit/d88be08feee5a2b66851925fe1e5978502421f19))
* support class-file-policy annotation ([ad3d1d2](https://github.com/teletha/reincarnation/commit/ad3d1d2a7e598cb41416c8356213f3a6a51feae0))
* support native method ([34262d1](https://github.com/teletha/reincarnation/commit/34262d1b5917c52adbb3baa67950a873506bc4be))
* support the shorthand array initialization with declaraiton ([9ba7305](https://github.com/teletha/reincarnation/commit/9ba73052fb64916f8d565f5de2b67444b2be74af))
* support various chained-assignment patterns ([5d38553](https://github.com/teletha/reincarnation/commit/5d38553c5a89b2215ebd84d6afabd5bfce190064))


### Bug Fixes

* call method with assignment parameter ([5a0be17](https://github.com/teletha/reincarnation/commit/5a0be174dba899a32a54b27e5297349d90188621))
* error handling in test ([4ddfca0](https://github.com/teletha/reincarnation/commit/4ddfca0b8d7aa4f20c221799f84ae98b465c3ae6))
* OpenrandUtil#load(String) can accept the bytecode type name ([070dd2e](https://github.com/teletha/reincarnation/commit/070dd2e14c9c4202c631148c4966661c37c43b33))
* Reincarnation cannot access to jdk-core-classes ([0df4951](https://github.com/teletha/reincarnation/commit/0df495181ee351e48c1c7d9e6ac59e058db66c7f))
* remove unused dependency (psychopath) ([d6b536d](https://github.com/teletha/reincarnation/commit/d6b536d5888de610aacd30d190910d60d429f7fa))
* specialization on parameterized type variable ([12b395e](https://github.com/teletha/reincarnation/commit/12b395ef270dcebcf17bf01460e3fad2d67e38d5))
* vararg coder is broken ([15c0bbb](https://github.com/teletha/reincarnation/commit/15c0bbb34434222070a382e7e9c9fc4c7fa5ba2c))

## [1.9.0](https://github.com/teletha/reincarnation/compare/v1.8.0...v1.9.0) (2024-11-11)


### Features

* require Java21 ([7002086](https://github.com/teletha/reincarnation/commit/7002086c5611c9a810300290869a60752d191708))


### Bug Fixes

* remove dependency on icy manipulator ([88c4209](https://github.com/teletha/reincarnation/commit/88c420908e7af58f91386c2a525979f32c61c5ab))
* source code requires Java 21 coz the latest ECJ is not support 23 ([cacdd30](https://github.com/teletha/reincarnation/commit/cacdd30ae85d68e4c17c6479f0ab2add95118405))

## [1.8.0](https://github.com/teletha/reincarnation/compare/v1.7.0...v1.8.0) (2024-08-27)


### Features

* support declaring TypeVariable in method and constructor ([36f0586](https://github.com/teletha/reincarnation/commit/36f058650751a9dc8a4c148425034ce996182eea))
* support method reference by TypeVariable ([a495d1b](https://github.com/teletha/reincarnation/commit/a495d1b8f86f3e5ed09fe64a56e622e0aea580c1))

## [1.7.0](https://github.com/teletha/reincarnation/compare/v1.6.0...v1.7.0) (2024-07-17)


### Features

* (ecj) support nested string switch expression ([73e9343](https://github.com/teletha/reincarnation/commit/73e9343092aac4261b92bf418993b4b983d33553))
* (ecj) support string switch expression ([0dfcf5b](https://github.com/teletha/reincarnation/commit/0dfcf5bb0d9714d39e97a846b020061fbad71acd))
* (javac) partial support for finally block ([0774200](https://github.com/teletha/reincarnation/commit/07742002b98e9cecda5dbc870fab8427e027c8bb))
* (javac) partial support for finally block ([7446fbf](https://github.com/teletha/reincarnation/commit/7446fbf9cc1d2037c5ce4e9ac0d89b62bc7686fb))
* (javac) partial support for try-finally block ([304d196](https://github.com/teletha/reincarnation/commit/304d196ad5d4947065f071f7cc3384237fe4e0bb))
* (javac) support constructor reference ([69e4088](https://github.com/teletha/reincarnation/commit/69e40884a89c07c6aa57c2843d2de4e94d68bee7))
* (javac) support enum switch ([482f1b6](https://github.com/teletha/reincarnation/commit/482f1b68daebc87e13050bc5573bf8909f46b457))
* (javac) support primitive array ([54ee0b3](https://github.com/teletha/reincarnation/commit/54ee0b362c6eb0c49f4694d00b5df618d43da21b))
* (javac) support string switch statement ([3c261ce](https://github.com/teletha/reincarnation/commit/3c261cece95be0fa778d9ff90d144afb5f17d476))
* (Javac) support synthetic asstion field ([687f725](https://github.com/teletha/reincarnation/commit/687f72543c3270cec9e54da0c79bfd3e5ddfb4e1))
* (javac) support throw in switch ([be4424c](https://github.com/teletha/reincarnation/commit/be4424c4728c445b5d5a9adfa1bccba3a34f6923))
* automatic bytecode compare view on failed test ([20347ff](https://github.com/teletha/reincarnation/commit/20347ff6c9cf0b513c2e52efe0b9965c55c8012f))
* compiler type is configurable ([44f4603](https://github.com/teletha/reincarnation/commit/44f4603392c6476f569376b41dffcc03003aa6c5))
* format debugger info ([cb4cc5a](https://github.com/teletha/reincarnation/commit/cb4cc5a4180640a95ba08ab1d36567f35b5984c0))
* show bytecode diff on failed tests ([82ce348](https://github.com/teletha/reincarnation/commit/82ce348a9f604260d3e42224127a8aabddd3e43e))
* show javac and ecj bytecodes on test error ([65853d3](https://github.com/teletha/reincarnation/commit/65853d3800b8cf6bbca61aec395331bf09f30409))
* support method condition on string switch ([a61f9a3](https://github.com/teletha/reincarnation/commit/a61f9a370f932a34d6ced75b16b37410cee22e0a))
* support try-catch in switch expression ([b3906db](https://github.com/teletha/reincarnation/commit/b3906db4cac0e912c28c7cf66ed9e23220ff7f74))
* test by ecj and javac ([9c1893f](https://github.com/teletha/reincarnation/commit/9c1893f57fb1cc3cbe2cbf2a571c80f31fe27865))


### Bug Fixes

* (javac) can't decompile local class ([efed2a0](https://github.com/teletha/reincarnation/commit/efed2a047799377d91474595d5586547d45a5192))
* (Javac) Executable#getGenericParameterTypes is broken on local ([6aa3a18](https://github.com/teletha/reincarnation/commit/6aa3a1817413d532cd4639ff04f50097eeaef2d1))
* (Javac) Executable#getGenericParameterTypes is broken. ([f576a0a](https://github.com/teletha/reincarnation/commit/f576a0a3496258566890fef8bd3dd375776ec364))
* (javac) optimize immediate yield in switch expression ([6e511e0](https://github.com/teletha/reincarnation/commit/6e511e05b23d889416253c16cb4a95dfe2efaafd))
* (javac) remove copied finally block correctly ([b1349d0](https://github.com/teletha/reincarnation/commit/b1349d0c876278eb6a199f2281ceb7f1467ff1cc))
* (javac) remove copied finally nodes correctly ([9fa8a53](https://github.com/teletha/reincarnation/commit/9fa8a53d03168d967f253d32007e21dde3990983))
* (javac) support string switch expression ([189c3ff](https://github.com/teletha/reincarnation/commit/189c3ff2c85c7be84c07461771c5dc5c63cb3d80))
* add Node#canReachToAny and #canBeReachedFromAny ([1575ca3](https://github.com/teletha/reincarnation/commit/1575ca3bdb425b19f9c6b6b31f281e12771ee9e3))
* cross compiler more ([76b362c](https://github.com/teletha/reincarnation/commit/76b362c95b1c74655246d2d25854feb6602ddd70))
* failed to decompile lambda ([9cebeb7](https://github.com/teletha/reincarnation/commit/9cebeb728d7bd1a162a356c3144f6c5a94dee85a))
* invalid if condition flow ([e45b3f7](https://github.com/teletha/reincarnation/commit/e45b3f749e67fcc62c1eb1d4d8e4dc16eab392fa))
* merge conditions correctly ([4b6550b](https://github.com/teletha/reincarnation/commit/4b6550b06789c7243d69579c6d609a7cf01392eb))
* nested switch expression ([7d236c5](https://github.com/teletha/reincarnation/commit/7d236c53d44c89e81f8055f7784b52bd7334f1f4))
* test cache the expected result ([18e2153](https://github.com/teletha/reincarnation/commit/18e215394913a7b84ca0ef22c23bcf65d768ab1b))

## [1.6.0](https://github.com/teletha/reincarnation/compare/v1.5.0...v1.6.0) (2024-04-21)


### Features

* append local variable index on Coder ([34d53b9](https://github.com/teletha/reincarnation/commit/34d53b981defc7fe1fc6c39ca64e3f39474650b9))
* Disable continue and break on return and yield statement. ([45058f3](https://github.com/teletha/reincarnation/commit/45058f3098d1bbd2657651a48e4693165248c6e6))
* Optimize the immediate yield partially. ([58be4e0](https://github.com/teletha/reincarnation/commit/58be4e0c6b514ce1f415afe2ed8e89c54fd4566c))
* support JEP 280 Indify String Concatenation ([a300498](https://github.com/teletha/reincarnation/commit/a3004988efacc2f0e6cdbe5aab1bcc9703c7357c))
* Support non-last default case on switch statement. ([0de49bb](https://github.com/teletha/reincarnation/commit/0de49bb6bf175e10be8c0daa42972581bb6a3935))
* Support throw on switch expression. ([a8cb756](https://github.com/teletha/reincarnation/commit/a8cb7569cfdc47cbb018ab59310ecc5eea9c9b69))
* update ecj to 3.37.0 ([017f484](https://github.com/teletha/reincarnation/commit/017f484aff0f6fef8c268088ff33bb9360925e17))


### Bug Fixes

* Avoid excessive merging of conditional nodes. ([50e7275](https://github.com/teletha/reincarnation/commit/50e72758fa89dbefc3e5410c37c4ab2e5bd560eb))
* update ci process ([b778669](https://github.com/teletha/reincarnation/commit/b7786699d4a65799916afc399b1551f056cecbce))
* update license ([a8eb09b](https://github.com/teletha/reincarnation/commit/a8eb09b9a9c982c9072a544808e7f20a47a7479a))
* update sinobu ([ae7541c](https://github.com/teletha/reincarnation/commit/ae7541c510a87a77669f415d243a786907009677))
* Use vineflower at test. ([a4514ad](https://github.com/teletha/reincarnation/commit/a4514ad6e2d05ae405e943cbced45a207f0043fa))

## [1.5.0](https://github.com/teletha/reincarnation/compare/v1.4.0...v1.5.0) (2023-03-31)


### Features

* Support omittable yield in switch statement. ([eaea4ec](https://github.com/teletha/reincarnation/commit/eaea4eca083548478f6142f2b671363db6fc548c))
* Support omittable yield on switch expression. ([9dd20bc](https://github.com/teletha/reincarnation/commit/9dd20bc6834fcd76d9a983ad2e030042fb7056d6))
* Support switch expression by char. ([e35760a](https://github.com/teletha/reincarnation/commit/e35760a5a2179ee95a2c2861eabbb3157cc66e9b))
* Support switch expression by enum. ([78e69e7](https://github.com/teletha/reincarnation/commit/78e69e72502c8b381edfa36fd5481b30b69fd544))
* Support switch expression by string. ([e9e5b1a](https://github.com/teletha/reincarnation/commit/e9e5b1a440f631b56cd4fdd1f75476c11f1ff862))
* Support switch expression partially. ([35aed41](https://github.com/teletha/reincarnation/commit/35aed4149e271351ac4ebcc12d637d1bdf19135f))
* Support yield on switch expression. ([d1d0655](https://github.com/teletha/reincarnation/commit/d1d06550b439dc071783610b92a4fa894ef1d337))


### Bug Fixes

* Detect the correct following node on if statement. ([48bba83](https://github.com/teletha/reincarnation/commit/48bba835ec7ded57cba2d3c2396affac3875a7b9))
* Distinguish between switch expressions and catch blocks. ([8a7840f](https://github.com/teletha/reincarnation/commit/8a7840f734144ca84f167283555ffba895bf8215))
* Distinguish between switch expressions and finally blocks. ([add9a3e](https://github.com/teletha/reincarnation/commit/add9a3e15596ac6f2445e87d9f63b8b2077ea3fa))
* Enable variable declaration on switch expression's default block. ([9e1686e](https://github.com/teletha/reincarnation/commit/9e1686e93226b1efb5b34da68f231a181c5c6a38))

## [1.4.0](https://github.com/teletha/reincarnation/compare/v1.3.0...v1.4.0) (2023-03-24)


### Features

* Omittable break label. ([1a30239](https://github.com/teletha/reincarnation/commit/1a302391b06390ec7a95e8ab027c0c6bca7953b8))
* Support escape sequence in string literal. ([8d86e8c](https://github.com/teletha/reincarnation/commit/8d86e8c6ebfac0a1e3ea4f1dd038f5c131b761ff))
* Support multiple cases on string switch. ([b622d2f](https://github.com/teletha/reincarnation/commit/b622d2f0083e1d399e29b15b2d2cdb1401b7bcfc))
* Support repeatable annotation. ([69f6a1f](https://github.com/teletha/reincarnation/commit/69f6a1f8e5f8956e770e887d3868efebe70377d7))
* Support switch statement by char. ([dd640c7](https://github.com/teletha/reincarnation/commit/dd640c7645c1b4691e5e4dceb936626c33263e44))
* Support switch statement by enum. ([59ef662](https://github.com/teletha/reincarnation/commit/59ef662bf62ea6ee23adbe5a6026e9f6e4fa67a6))
* Support switch statement by String. ([30898a0](https://github.com/teletha/reincarnation/commit/30898a0938ed6c9072fb5e5d0ba687b92c2da76b))
* Support switch statement for integral number. ([c5dbcca](https://github.com/teletha/reincarnation/commit/c5dbccaa5b717d626e8a15f8d24dd47412b1478e))
* Support switch statement partially. ([cf8b475](https://github.com/teletha/reincarnation/commit/cf8b4758cc9fd080a472053d8bcaebabbd0a3a46))
* Support text block. ([277da89](https://github.com/teletha/reincarnation/commit/277da891450bc3919ed2463d68e39583deb1fd5b))


### Bug Fixes

* Hide Debugger. ([45ce4a6](https://github.com/teletha/reincarnation/commit/45ce4a6e6904f329d7e72eb4c09d93c8459d57bc))
* Normalize the sequencial jump instructions. ([3972ec7](https://github.com/teletha/reincarnation/commit/3972ec7bcf632e755529215d2cc2e0851ad073d1))
* Omit empty default block on switch. ([a48a7f6](https://github.com/teletha/reincarnation/commit/a48a7f64b9b64a7dc27367cf4de28c983a3904cf))

## [1.3.0](https://github.com/teletha/reincarnation/compare/v1.2.0...v1.3.0) (2023-03-16)


### Features

* (Java) Optimize output for the implicit constructor. ([02664f1](https://github.com/teletha/reincarnation/commit/02664f12eb8211f53bc8a6615d002232b45fa40b))
* Support annotation on annotation and parameter. ([d2c5957](https://github.com/teletha/reincarnation/commit/d2c5957c22ab58c885b8927e146651406ce1f3c3))
* Support annotation on type, constructor, method and field. ([c0df1ca](https://github.com/teletha/reincarnation/commit/c0df1ca178e4132576bd64dadb06257dd0f44320))
* Support enhanced for loop by array. ([e70e99c](https://github.com/teletha/reincarnation/commit/e70e99c4104cdb79bed2a758bb05eadb43d6b438))
* Support enum features completely. ([b046aa1](https://github.com/teletha/reincarnation/commit/b046aa10e27b0ac7980ee9088b922be0fc804ecd))
* Support enum. ([1db1026](https://github.com/teletha/reincarnation/commit/1db1026c467c8c3b960b2eb7ec0b92d5af8437ec))
* Support extending class. ([e985499](https://github.com/teletha/reincarnation/commit/e9854996837eb472ffaa8ea620452be1a6addb50))
* Support GenericArrayType. ([8dae090](https://github.com/teletha/reincarnation/commit/8dae09051ecf9a8d86470d8d0e826316bd43164b))
* Support record features. ([82ddb15](https://github.com/teletha/reincarnation/commit/82ddb159273fca01574604716e57de99f1c4593a))
* Support sealed class. ([4b8bf11](https://github.com/teletha/reincarnation/commit/4b8bf11a72d42ac09ffb72c081a6db294a3788bd))


### Bug Fixes

* (Java) Optimize super() code writing. ([3e16baf](https://github.com/teletha/reincarnation/commit/3e16baf2252b89363ecf77625bf647ab55ea5bc1))
* Don't use Parameter#isSynthetic. ([b944714](https://github.com/teletha/reincarnation/commit/b9447141da7a86b03cef119d01842a6c9f465f5b))
* Hide OperandLocalVariable and Inference. ([a4cf1dd](https://github.com/teletha/reincarnation/commit/a4cf1dd07ec3a1dc0233a262c608b3ccdd3131f0))
* Hide some operand types. ([de4ebca](https://github.com/teletha/reincarnation/commit/de4ebca488bab366b925c96099898168232ce460))
* Hide some operand types. ([fa2db31](https://github.com/teletha/reincarnation/commit/fa2db3160db418b15e034c972a76aa67496ba44a))
* Move OperandUtil#isWrapper and #isUnwrapper to Classes. ([adcf70b](https://github.com/teletha/reincarnation/commit/adcf70bfffdfcd37eb0b1b27874c9e2bb7376b8f))
* Update ecj compiler. ([ce4a520](https://github.com/teletha/reincarnation/commit/ce4a5205630aa8ebb7b7194204ec27ba2dd81e55))

## [1.2.0](https://github.com/teletha/reincarnation/compare/v1.1.0...v1.2.0) (2023-03-13)


### Features

* (Java) Import classes are filtered, sorted and grouped. ([1627e9a](https://github.com/teletha/reincarnation/commit/1627e9a6c522aa2f29a522bcceab37ea68fb72c8))
* Optimize class literal for primitive types. ([35d9530](https://github.com/teletha/reincarnation/commit/35d9530b3fa1ed3e927ab8da20b6b4ecd0ab761e))
* Provide specialized ParameterizedType. ([5f1da9c](https://github.com/teletha/reincarnation/commit/5f1da9ca8b932aa6f7d51185345d847a8f7db481))
* Support all modifiers. ([ae17983](https://github.com/teletha/reincarnation/commit/ae1798328ab9e6d58be70f337b1f1465b64ed6bc))
* Support assert with message. ([375d034](https://github.com/teletha/reincarnation/commit/375d0347b788cd366f6db2ab480fd4a580c34c01))
* Support generics on type definition. ([5c811dd](https://github.com/teletha/reincarnation/commit/5c811dd1ec2636f014a88f036f1317532dbf72a7))
* Support generics partially. ([b3f0219](https://github.com/teletha/reincarnation/commit/b3f0219a174c3fd8aaec8249bf41a3c439e7f88b))
* Support lambda with contextual local variables. ([098d7f5](https://github.com/teletha/reincarnation/commit/098d7f550aa66e9f3069ed0e30bfed8938f85566))
* Support local class. ([5dcc7e1](https://github.com/teletha/reincarnation/commit/5dcc7e1667e1b53a427a3876999a9fe2aac040c9))
* Support method reference for array and constructor. ([1a47b33](https://github.com/teletha/reincarnation/commit/1a47b330b6c30295b4fa90234608e856236343cc))
* Support method reference. ([63aaa91](https://github.com/teletha/reincarnation/commit/63aaa91ac041a217c35ac24d2ebcfe7bf72a81d3))
* Support nested lambda with contextual variables. ([094c379](https://github.com/teletha/reincarnation/commit/094c379e87f710d4241a8927c3786d2187b7bc74))
* Support static method and private method on interface. ([9abb142](https://github.com/teletha/reincarnation/commit/9abb142aafe4b570a3fd1aa7ce40a176800aeaca))
* Support the bounded type variable on type definition. ([e7ec82a](https://github.com/teletha/reincarnation/commit/e7ec82adb9cc273ffe1fcf8f260e770c0ba47163))
* Support vararg. ([7759450](https://github.com/teletha/reincarnation/commit/775945052831481cf9a1a9cfa9de0df9b1b63bd6))


### Bug Fixes

* Capture assertion error if needed. ([ef5f894](https://github.com/teletha/reincarnation/commit/ef5f894bf399d89c0aa51b838fe1f0e92784bd96))
* Enable assertion on verify tests. ([16ea2cc](https://github.com/teletha/reincarnation/commit/16ea2cc2030865c04ec7b125a6fbe565f81caaf7))
* Optimize cast code. ([6321803](https://github.com/teletha/reincarnation/commit/6321803022607286ec359533035306c8618c0b5c))
* remove duplicated code ([55b50ba](https://github.com/teletha/reincarnation/commit/55b50ba28c33f57295e838e2f63fecc9861a29ed))

## [1.1.0](https://github.com/teletha/reincarnation/compare/v1.0.0...v1.1.0) (2023-03-02)


### Features

* (Java) Hide wrapper method call. ([0ece9ec](https://github.com/teletha/reincarnation/commit/0ece9ec685b12c1d9027fd6c1b1b140b136e7f39))
* Drop Reincarnation#rebirth(Class). ([fa15a22](https://github.com/teletha/reincarnation/commit/fa15a221fefdf5d3b1a06c4d443530cb31ba3ece))
* Enable fernflower on debug mode. ([f3122d5](https://github.com/teletha/reincarnation/commit/f3122d5e3d441dcecf8efd5cd4981000732469e2))
* Operand matching engine supports optional value. ([3448d45](https://github.com/teletha/reincarnation/commit/3448d45c665c981343ca98886dcd8e0d60e94f0a))
* OperandUtil#load supports array related operands. ([c9cf309](https://github.com/teletha/reincarnation/commit/c9cf309dfe398c667adddfdd91ddf6a3630ec6f2))
* OperandUtil#load supports object array related operands. ([05d5f9c](https://github.com/teletha/reincarnation/commit/05d5f9c56c1b33be1706a41a03f26bf6d71f00df))
* Optimize boolean condition. ([a346267](https://github.com/teletha/reincarnation/commit/a3462678d9409b5095fb1dac0ed5271496760b9a))
* Optimize empty if or else block. ([7d4e1e3](https://github.com/teletha/reincarnation/commit/7d4e1e30c5c8a04e5ddaa9d78fbd2824144a4b67))
* Optimize instanceof with pattern matching. ([fc6366c](https://github.com/teletha/reincarnation/commit/fc6366cce254a8bb101a8ed320103d6cfa78bcb3))
* Provide Naming strategy. ([d8cb5fe](https://github.com/teletha/reincarnation/commit/d8cb5fe7e7adf3973fbceccb893933cfa96af4bc))
* Remove InferredType. ([a856c55](https://github.com/teletha/reincarnation/commit/a856c555fd66e0053ca3ea136f1fcd615ddc6384))
* Support duplicated variable usage. ([da7862e](https://github.com/teletha/reincarnation/commit/da7862e72a9dc5fe4e33b80aef2d13f0a3d379c9))
* Support enhanced for loop. ([10f1614](https://github.com/teletha/reincarnation/commit/10f1614740573ebcc609a3ba34211a71f7f82ed0))
* Support enhanced for-loop for primitive wrapper classes. ([7defc41](https://github.com/teletha/reincarnation/commit/7defc416589d720c62d6437335a00d16effc5785))
* Support enhanced for-loop with break. ([1e77f61](https://github.com/teletha/reincarnation/commit/1e77f61a4ba8ecadf21d5701da8d8c0ec703a512))
* Support negation of instanceof. ([982292d](https://github.com/teletha/reincarnation/commit/982292d987ea6591ce957b3245a8f8f50376e47b))
* Support original variable name if available. ([7d5dba6](https://github.com/teletha/reincarnation/commit/7d5dba685c63b4f7f8558107ed33ebc2fb27bfe3))
* Support pattern matching for instanceof. ([10f7218](https://github.com/teletha/reincarnation/commit/10f7218afe4edc67e6b0368c19d57b95956b846a))
* Support reusable variable declaration. ([1624248](https://github.com/teletha/reincarnation/commit/162424885b955e919c86171382190f5c2c13820d))
* Support string concatenation. ([58bd29d](https://github.com/teletha/reincarnation/commit/58bd29dbdfaef7dc3f171b2dd7ddc099442ecfe9))


### Bug Fixes

* Enable debuggable on class. ([e252e17](https://github.com/teletha/reincarnation/commit/e252e175332f4145a2c7b36cdd43508758b1b64b))
* OperandInstanceOf must infer its type as boolean. ([d27f813](https://github.com/teletha/reincarnation/commit/d27f813393ac10ad7ec4e7304f8bce0964b1f100))
* OperandLocalVariable must be stateless. ([84180f4](https://github.com/teletha/reincarnation/commit/84180f417172e2562f1a03945b2452489d35a098))
* Support ALOAD oprand in Util#load. ([4d5741c](https://github.com/teletha/reincarnation/commit/4d5741c05ee86318395f76a52e6393c057df010a))

## 1.0.0 (2023-02-23)


### Features

* debuggable annotaion is enabled at test class and method ([5476927](https://github.com/teletha/reincarnation/commit/5476927e9403dc4914e351f22e78227a21f6a480))
* Debugger is thread-safe. ([89e0935](https://github.com/teletha/reincarnation/commit/89e09354057c5572608f8302eb39901396d15f33))
* enhance debug message ([6e22ff8](https://github.com/teletha/reincarnation/commit/6e22ff88bb40109a12e69f3d989d4490d61d1b91))
* Failed test uses debug mode automatically. ([2cbff2e](https://github.com/teletha/reincarnation/commit/2cbff2e21e6ad29f1f83d426dbad2eda8272b7e5))
* Optimize the immediate return. ([e548b59](https://github.com/teletha/reincarnation/commit/e548b59e2c7cd74b604166900fe400686e473549))
* Optimize value assignment. ([35b62fd](https://github.com/teletha/reincarnation/commit/35b62fdc7b4a9910acf457ff5a7dabc0a3325f2c))
* Provide Decompiler API. ([74fb411](https://github.com/teletha/reincarnation/commit/74fb411a7912e313a0c58ae210001801866b05a0))


### Bug Fixes

* debug mode causes some error ([5b70343](https://github.com/teletha/reincarnation/commit/5b70343965d115d4306666ec7404d37c4717aaf4))
* Duplicated variable name. ([faba88e](https://github.com/teletha/reincarnation/commit/faba88e4d56ebe6a977e204b455fd213093ce924))
* Pass try-finally tests. ([df2eac3](https://github.com/teletha/reincarnation/commit/df2eac33026389b3a2f909dd336a54ea7ed941ab))
* Pass TryCatchFinally tests. ([ebf2574](https://github.com/teletha/reincarnation/commit/ebf2574895ca50278d122cf1bb3662463aa963a7))
* Update bee. ([28e91fb](https://github.com/teletha/reincarnation/commit/28e91fbd12e3cf6a01664b4aebfcf78d138ec85b))
* update viewtify ([1043693](https://github.com/teletha/reincarnation/commit/104369346dae6f0bf6f5637c2c30387f2e11484e))
