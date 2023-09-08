Profil Kimlik Doğrulama:

Metod	URL	Tanım
POST	${URL}/profile/register	Kayıt Ol
POST	${URL}/profile/logout	Çıkış Yap
POST	${URL}/profile/login	Giriş Yap
Tweet İşlemleri:

Metod	URL	Tanım
GET	${URL}/tweet/	Bütün Tweetleri Getir
GET	${URL}/tweet/id	Bir Tweet Getir
POST	${URL}/tweet	Yeni Tweet
PUT	${URL}/tweet/id	Tweet Düzenle
DELETE	${URL}/tweet/id	Tweet Sil
POST	${URL}/tweet/like/id	Beğen
DELETE	${URL}/tweet/like/id	Beğenme
POST	${URL}/tweet/retweet/id	Retweet
POST	${URL}/tweet/reply/id	Yorum Yap
DELETE	${URL}/tweet/reply/id	Yorum Sil