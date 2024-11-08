package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class MainController {
    public static void main(String[] args) {
//        1.基于Token的分离（无状态）
//        基于Token的前后端分离主打无状态，无状态服务是指在处理每个请求时，服务本身不会维持任何与请求相关的状态信息。
//        也就是说，用户在发起请求时，服务器不会记录其信息，而是通过用户携带的Token信息来判断是哪一个用户

//        2.认识JWT令牌
//        在认识Token前后端分离之前，我们需要先学习最常见的JWT令牌，官网：https://jwt.io

//        JWT令牌的格式如下：
//        3.一个JWT令牌由3部分组成：标头(Header)、有效载荷(Payload)和签名(Signature)。
//        在传输的时候，会将JWT的前2部分分别进行Base64编码后用.进行连接形成最终需要传输的字符串。
//
//        标头：包含一些元数据信息，比如JWT签名所使用的加密算法，还有类型，这里统一都是JWT。
//        有效载荷：包括用户名称、令牌发布时间、过期时间、JWT ID等，当然我们也可以自定义添加字段，我们的用户信息一般都在这里存放。
//        签名：首先需要指定一个密钥，该密钥仅仅保存在服务器中，保证不能让其他用户知道。
//        然后使用Header中指定的算法对Header和Payload进行base64加密之后的结果通过密钥计算哈希值，然后就得出一个签名哈希。
//        这个会用于之后验证内容是否被篡改。

//        4.Base64：就是包括小写字母a-z、大写字母A-Z、数字0-9、符号"+"、"/"一共64个字符的字符集（末尾还有1个或多个=用来凑够字节数），
//        任何的符号都可以转换成这个字符集中的字符，这个转换过程就叫做Base64编码，编码之后会生成只包含上述64个字符的字符串。
//        相反，如果需要原本的内容，我们也可以进行Base64解码，回到原有的样子。
//
//        注意Base64不是加密算法，只是一种信息的编码方式而已。
//
//        Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        //Base64不只是可以对字符串进行编码，任何byte[]数据（图片、文件）都可以，编码结果可以是byte[]，也可以是字符串
        String str = "Hello";
        String encodeStr = encoder.encodeToString(str.getBytes());
        System.out.println("Base64编码后的字符串："+encodeStr);
//        Base64解码
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encodeStr);
        System.out.println("解码后的字符串："+new String(decode));

//        5.加密算法：加密算法分为对称加密和非对称加密
//        不可逆加密算法：
//        对称加密，就像一把锁配了两把钥匙一样，这两把钥匙你和别人都有一把，然后你们直接传递数据，都会把数据用锁给锁上，
//        就算传递的途中有人把数据窃取了，也没办法解密，因为钥匙只有你和对方有，没有钥匙无法进行解密
//
//        非对称加密，它并不是直接生成一把钥匙，而是生成一个公钥和一个私钥，私钥只能由你保管，而公钥交给对方或是你要发送的任何人都行，
//        现在你需要把数据传给对方，那么就需要使用私钥进行加密，但是，这个数据只能使用对应的公钥进行解密，
//        相反，如果对方需要给你发送数据，那么就需要用公钥进行加密，而数据只能使用私钥进行解密，
//        这样的话就算对方的公钥被窃取，那么别人发给你的数据也没办法解密出来，因为需要私钥才能解密，而只有你才有私钥。
//
//        因此，非对称加密的安全性会更高一些，包括HTTPS的隐私信息正是使用非对称加密来保障传输数据的安全
//        （当然HTTPS并不是单纯地使用非对称加密完成的，感兴趣的可以去了解一下）
//
//        对称加密和非对称加密都有很多的算法，比如对称加密，就有：DES、IDEA、RC2，非对称加密有：RSA、DAS、ECC

//        6.JWT令牌实际上是一种经过加密的JSON数据，其中包含了用户名字、用户ID等信息，我们可以直接解密JWT令牌得到用户的信息，
//
//        给用户JWT令牌时，最好不要把时间给太长了，比如改用户权限啥的，使令牌失效比价麻烦，失效时间最好短一点，甚至几个小时都可以
//
//        JWT实际上最后会有一个加密的签名，这个是根据秘钥+JWT本体内容计算得到的，用户在没有持有秘钥的情况下，是不可能计算得到正确的签名的，
//        所以说服务器会在收到JWT时对签名进行重新计算，比较是否一致，来验证JWT是否被用户恶意修改，如果被修改肯定也是不能通过的。
//
//        要生成一个JWT令牌非常简单
//        密钥key
        String jwtKey = "abcdefghijklmn";                 //使用一个JWT秘钥进行加密
//        指定加密算法
        Algorithm algorithm = Algorithm.HMAC256(jwtKey);  //创建HMAC256加密算法对象
        String jwtToken = JWT.create()
                .withClaim("id", 1)   //向令牌中塞入自定义的数据
                .withClaim("name", "lbw")
                .withClaim("role", "nb")
//                JWT令牌的失效时间
                .withExpiresAt(new Date(2024, Calendar.FEBRUARY, 1))
//                JWT令牌的签发时间
                .withIssuedAt(new Date())
                .sign(algorithm);    //使用上面的加密算法进行加密，完成签名
        System.out.println(jwtToken);   //得到最终的JWT令牌
//        我们可以使用Base64将其还原为原本的样子
        String jwtTokens = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoibmIiLCJuYW1lIjoibGJ3IiwiaWQiOjEsImV4cCI6NjE2NjQ4NjA4MDAsImlhdCI6MTcwNTU1NzE3Nn0.Rvs5D1YS22M97LX9Gc6SVPCV-BhGD9F-nJ97la5eVvY";
        String[] split = jwtTokens.split("\\.");
        for (int i = 0; i < split.length - 1; i++) {
            String s = split[i];
            byte[] decodes = Base64.getDecoder().decode(s);
            System.out.println(new String(decodes));
        }

    }
}
