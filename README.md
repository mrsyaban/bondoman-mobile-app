# IF3210-2024-Android-PSI
<h2 align="center">
    Bondoman : Financial App in Android<br/>
</h2>
<hr>

## Daftar Isi
1. [Deskripsi Singkat](#deskripsi-singkat)
2. [Penggunaan Library](#penggunaan-library)
3. [Screenshots](#screenshots)
4. [Pembagian Kerja](#pembagian-kerja)
5. [Jam Kerja](#jam-kerja)

<a name="deskripsi-singkat"></a>

## Diskripsi Singkat
Aplikasi Bondoman adalah aplikasi yang dikembangkan untuk memenuhi tugas besar mata kuliah IF3210 Pemrograman Aplikasi pada Platform Khusus. Bondoman merupakan sebuah aplikasi berbasis Android dengan bahasa Kotlin yang dapat membantu kita untuk mengatur keuangan kita sehari-hari dengan fitur-fitur yang tersedia di dalamnya.

Fitur-fitur yang tersedia di dalam aplikasi Bondoman, antara lain:
1. Header dan Navbar:
    - Identifier untuk tiap halaman
    - Navigasi antar halaman 
2. Halaman Login:
    - Masuk ke dalam aplikasi dengan menggunakan akun yang sudah terdaftar (Background Service)
3. Halaman Settings:
    - Men-generate transaksi secara random (Broadcast Receiver)
    - Men-download data transaksi sebagai file *.xlsx atau *.xls
    - Mengirim data transaksi melalui email (Intent G-Mail)
    - Logout dari aplikasi
4. Halaman Transakssi:
    - Menampilkan daftar transaksi yang sudah dilakukan
    - Menambahkan transaksi baru
    - Menghapus transaksi yang sudah
    - Mengedit transaksi yang sudah ada
5. Halaman Scan Nota:
    - Melakukan scan nota atau mengupload gambar nota
6. Halaman Graf/Statistik:
    - Menampilkan grafik / statistik dari transaksi yang sudah dilakukan
7. Halaman Twibbon:
    - Mengambil gambar dengan twibbon Bondoman
    - Mengganti gambar twibbon yang hendak digunakan
8. Halaman Offline:
    - Menampilkan halaman offline apabila tidak terdeteksi ada koneksi internet

<a name="penggunaan-library"></a>

## Penggunaan Library
Pada proses pengerjaannya, kami menggunakan beberapa library yang dapat membantu kami dalam mengembangkan aplikasi Bondoman. Berikut adalah daftar library yang kami gunakan:
1. AndroidX Core KTX (androidx.core:core-ktx:1.12.0)
2. AndroidX AppCompat (androidx.appcompat:appcompat:1.6.1)
3. Material Components for Android (com.google.android.material:material:1.11.0)
4. ConstraintLayout (androidx.constraintlayout:constraintlayout:2.1.4)
5. AndroidX Lifecycle Extensions (androidx.lifecycle:lifecycle-livedata-ktx:2.4.0, androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0, androidx.lifecycle:lifecycle-runtime-ktx:2.4.0)
6. AndroidX Camera (androidx.camera:camera-core:1.1.0, androidx.camera:camera-camera2:1.1.0, androidx.camera:camera-lifecycle:1.1.0, androidx.camera:camera-video:1.1.0, androidx.camera:camera-view:1.1.0, androidx.camera:camera-extensions:1.1.0)
7. AndroidX Room (androidx.room:room-common:2.6.1, androidx.room:room-runtime:2.6.1, androidx.room:room-ktx:2.6.1)
8. JUnit for testing (junit:junit:4.13.2)
9. AndroidX Test Extensions (androidx.test.ext:junit:1.1.5)
10. Espresso Core for UI testing (androidx.test.espresso:espresso-core:3.5.1)
11. AndroidX Activity KTX (androidx.activity:activity-ktx)
12. AndroidX Hilt Navigation Compose (androidx.hilt:hilt-navigation-compose:1.2.0)
13. Retrofit for making HTTP requests (com.squareup.retrofit2:retrofit:2.9.0, com.squareup.retrofit2:converter-gson:2.9.0)
14. OkHttp Logging Interceptor (com.squareup.okhttp3:logging-interceptor:4.9.3)
15. AndroidX Navigation (androidx.navigation:navigation-fragment-ktx:2.7.7, androidx.navigation:navigation-ui-ktx:2.7.7)
16. Dagger Hilt for dependency injection (com.google.dagger:hilt-android:2.51.1, com.google.dagger:hilt-compiler:2.51.1)
17. MultiDex for handling apps with more than 64K methods (androidx.multidex:multidex:2.0.1)
18. MPAndroidChart for displaying charts (com.github.PhilJay:MPAndroidChart:v3.1.0)
19. Google Play Services Location (com.google.android.gms:play-services-location:21.2.0)
20. Apache POI for working with Excel files (org.apache.poi:poi:5.1.0, org.apache.poi:poi-ooxml:5.1.0)

<a name="screenshots"></a>

## Screenshots Aplikasi
Berikut adalah beberapa tampilan dari aplikasi Bondoman:
<p>
  <img src="/screenshots/1.png/">
  <p>Gambar 1.</p>
  <nl>
  <img src="/screenshots/2.png/">
  <p>Gambar 2.</p>
  <nl>
  <img src="/screenshots/3.png/">
  <p>Gambar 3.</p>
  <nl>
</p>

<a name="pembagian-kerja"></a>

## Pembagian Kerja
| Nama                        | NIM      | Pembagian Kerja                                                    |
| --------------------------- | -------- | ------------------------------------------------------------------ |
| Vieri Fajar Firdaus         | 13521099 | Login, Background Service, Graph, Intent Email, Broadcast Receiver |
| Muhammad Rizky Sya'ban      | 13521119 | Transaksi (penambahan, penghapusan, update)                        |
| Mohammad Rifqi Farhansyah   | 13521166 | Design, Scan, Twibbon, Download History (*.xlsx & .xls), Splash    |

<a name="jam-kera"></a>

## Jam Kerja
| Nama                        | NIM      | Persiapan | Pengerjaan |
| --------------------------- | -------- | --------- | ---------- |
| Vieri Fajar Firdaus         | 13521099 | 24 Jam    | 50 Jam     |
| Muhammad Rizky Sya'ban      | 13521119 | 24 Jam    | 50 Jam     |
| Mohammad Rifqi Farhansyah   | 13521166 | 24 Jam    | 50 Jam     |