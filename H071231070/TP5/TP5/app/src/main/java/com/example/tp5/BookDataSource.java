package com.example.tp5;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BookDataSource {
    private static List<Book> bookList = new ArrayList<>();
    private static List<Book> favoriteBooks = new ArrayList<>();

    public static void generateDummyBooks(Context context) {
        if (!bookList.isEmpty()) return;

        bookList.add(new Book("Bumi", "Tere Liye", "2014",
                "Namaku Raib, usiaku 15 tahun. Aku adalah remaja biasa, kecuali satu hal: aku bisa menghilang. Tidak ada yang tahu, bahkan orang tuaku. Semua berubah saat seorang pria asing berwajah pucat tiba-tiba muncul di kamarku. Ia memanggilku ‘anak manis’, dan kehadirannya mengubah seluruh hidupku. Bersama teman-temanku, Ali dan Seli, aku menemukan portal rahasia menuju dunia lain, dunia yang selama ini hanya ada dalam imajinasi: Dunia Pararel. Petualangan kami di dunia tersebut mempertemukan kami dengan makhluk-makhluk aneh, teknologi canggih, dan rahasia besar tentang siapa sebenarnya aku dan darimana asal usulku.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.bumi
        ));
        bookList.add(new Book("Harry Potter and the Sorcerer’s Stone", "J.K. Rowling", "1997",
                "Harry Potter thinks he is an ordinary boy — until he is rescued by a beetle-eyed giant of a man, enrolls at Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch, and does battle in a deadly duel. The Reason: Harry Potter is a wizard! This is the first book in the Harry Potter series and introduces readers to the young wizard, his friends, and the magical world of Hogwarts.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hp_and_the_sorcerers_stone
        ));
        bookList.add(new Book("Laut Bercerita", "Leila S. Chudori", "2017",
                "Laut Bercerita membawa kita ke dunia penuh idealisme anak-anak muda pada masa Orde Baru. Laut Wiji Thukul adalah seorang aktivis yang berjuang melawan ketidakadilan, penangkapan sewenang-wenang, dan kekerasan negara. Melalui kisah Laut dan kawan-kawannya yang ditangkap dan hilang, novel ini tidak hanya mengisahkan tentang pergulatan politik, tapi juga tentang kehilangan, keluarga, dan cinta yang tetap bertahan di tengah duka.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.laut_bercerita
        ));
        bookList.add(new Book("Bulan", "Tere Liye", "2015",
                "Petualangan Raib, Seli, dan Ali berlanjut ke klan Bulan, setelah perjalanan mereka di klan Matahari. Dunia ini dipenuhi makhluk aneh, teknologi luar biasa, dan tantangan berbahaya. Mereka harus menghadapi berbagai rintangan untuk menyelesaikan misi penting: mencari sumber kekuatan yang dapat menjaga keseimbangan dunia paralel. Di dunia Bulan, mereka menemukan persahabatan, pengkhianatan, dan pelajaran tentang keberanian serta pengorbanan.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.bulan
        ));
        bookList.add(new Book("Dunia Shopie", "Jostein Gaarder", "1991",
                "Sophie Amundsen, seorang gadis berusia 14 tahun, menerima surat misterius yang memulai perjalanan intelektualnya menjelajahi sejarah filsafat. Dari filsuf kuno seperti Socrates hingga pemikir modern seperti Sartre, Dunia Sophie membawa pembaca melewati pertanyaan-pertanyaan besar tentang keberadaan, kehidupan, dan alam semesta. Sebuah novel yang menggabungkan kisah fiksi dan pelajaran filsafat secara menarik dan mudah dipahami.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.dunia_sophie
        ));
        bookList.add(new Book("Harry Potter and The Deathly Hallows", "J.K. Rowling", "2007",
                "Harry Potter kini menghadapi misi paling berbahaya dalam hidupnya: menemukan dan menghancurkan Horcrux terakhir Voldemort. Tanpa bantuan guru dan perlindungan sihir Hogwarts, Harry harus mengandalkan keberanian, kekuatan persahabatan, dan tekadnya sendiri dalam pertempuran terakhir melawan kekuatan jahat. Dalam petualangan ini, kebenaran akan terungkap, pengorbanan akan dilakukan, dan dunia sihir akan berubah selamanya.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hp_and_the_deathly_hallows
        ));
        bookList.add(new Book("Gadis Kretek", "Ratih Kumala", "2012",
                "Gadis Kretek adalah kisah cinta berlatar sejarah industri rokok kretek di Indonesia. Melalui perjalanan tiga bersaudara yang mencari sosok Jeng Yah, cinta masa lalu ayah mereka, novel ini menyingkap kisah tentang ambisi, pengkhianatan, dan pergulatan identitas di tengah perubahan zaman. Ratih Kumala mengisahkan sejarah kretek tidak hanya sebagai produk budaya, tetapi juga sebagai saksi bisu pergolakan sosial dan politik negeri ini.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.gadis_kretek
        ));
        bookList.add(new Book("Harry Potter and The Goblet of Fire", "J.K. Rowling", "2000",
                "Dalam tahun keempatnya di Hogwarts, Harry Potter mendapati dirinya secara tak terduga terpilih untuk mengikuti Turnamen Triwizard—kompetisi sihir berbahaya antara tiga sekolah sihir terkemuka. Sementara Harry menghadapi tantangan-tantangan mematikan, dunia sihir mulai diliputi ketakutan oleh kembalinya kekuatan Lord Voldemort. Buku ini menandai peralihan dari kisah anak-anak ke cerita yang lebih gelap dan kompleks, memperlihatkan pertumbuhan karakter, persahabatan yang diuji, serta ancaman yang semakin nyata terhadap dunia sihir.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hp_and_the_goblet_of_fire
        ));
        bookList.add(new Book("Hujan", "Tere Liye", "2016",
                "Ini adalah kisah tentang persahabatan, tentang cinta, tentang perpisahan, dan tentang hujan. Lail, gadis kecil yang kehilangan segalanya karena bencana alam besar, menemukan makna hidup baru melalui pertemuannya dengan Esok. Dalam dunia masa depan yang penuh teknologi canggih, kisah Lail dan Esok mengajarkan tentang kekuatan memaafkan, melepaskan, dan menemukan kebahagiaan meski dihujani kehilangan.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hujan
        ));
        bookList.add(new Book("Komet", "Tere Liye", "2018",
                "Petualangan Raib, Seli, dan Ali berlanjut ke tempat-tempat baru di dunia paralel. Kali ini mereka mengejar komet yang membawa perubahan besar di berbagai klan. Dalam perjalanan penuh tantangan ini, mereka menghadapi musuh baru, bertemu sekutu tak terduga, dan semakin memahami pentingnya persahabatan, keberanian, serta pengorbanan untuk menyelamatkan dunia mereka.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.komet
        ));
        bookList.add(new Book("Lumpu", "Tere Liye", "2020",
                "Yes! Akhirnya, Raib, Seli, dan Ali kembali bertualang. Kalian sudah kangen dengan trio ini? Misi mereka adalah menyelamatkan Miss Selena, guru matematika mereka. Tapi, apakah semua berjalan mudah? Siapa yang bersedia membantu mereka? Kali ini, si genius Ali memutuskan meminta bantuan dari sosok yang tidak terduga, karena musuh dari musuh adalah teman. Apakah Raib bisa melupakan masa lalu itu dengan memaafkan Miss Selena? Bagaimana dengan Tazk? Apakah Raib bisa bertemu lagi dengan ayahnya, atau itu masih menjadi misteri? Bagaimana dengan jejak ekspedisi Klan Aldebaran 40.000 tahun lalu? Benda apa saja yang ditinggalkan oleh perjalanan besar tersebut? Pertarungan panjang telah menunggu mereka. Dan lawan mereka adalah Lumpu, petarung yang memiliki teknik unik, yaitu melumpuhkan kekuatan lawan. Itu teknik yang amat menakutkan, karena Lumpu bisa menghabisi teknik bertarung. Jangan-jangan… Siapa di antara Raib, Seli, dan Ali yang akan kehilangan kekuatan di dunia paralel?",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.lumpu
        ));
        bookList.add(new Book("Matahari", "Tere Liye", "2016",
                "Namanya Ali, 15 tahun, kelas X. Jika saja orangtuanya mengizinkan, seharusnya dia sudah duduk di tingkat akhir ilmu fisika program doktor di universitas ternama. Ali tidak menyukai sekolahnya, guru-gurunya, teman-teman sekelasnya. Semua membosankan baginya. Tapi sejak dia mengetahui ada yang aneh pada diriku dan Seli, teman sekelasnya, hidupnya yang membosankan berubah seru. Aku bisa menghilang, dan Seli bisa mengeluarkan petir. Ali sendiri punya rahasia kecil. Dia bisa berubah menjadi beruang raksasa.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.matahari
        ));
        bookList.add(new Book("Harry Potter and The Half-Blood Prince", "J.K. Rowling", "2005",
                "Harry Potter kembali ke tahun keenam di Hogwarts, di mana ia semakin dekat dengan misteri kelam yang mengelilingi Lord Voldemort. Dengan bantuan buku lama yang penuh catatan dari 'Half-Blood Prince,' Harry mempelajari sihir yang lebih kuat dan berbahaya, yang membawanya ke petualangan yang lebih gelap. Sementara itu, hubungan antara para karakter semakin berkembang dan teruji, dengan kesetiaan dan persahabatan diuji lebih dari sebelumnya. Namun, saat mereka berhadapan dengan rahasia besar dan ancaman yang semakin dekat, tragedi dan pengorbanan menanti.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hp_and_the_half_blood_prince
        ));
        bookList.add(new Book("Nebula", "Tere Liye", "2020",
                "Selena dan Nebula adalah buku ke-8 dan ke-9 yang menceritakan siapa orangtua Raib dalam serial petualangan dunia paralel. Dua buku ini sebaiknya dibaca berurutan. Kedua buku ini juga bercerita tentang Akademi Bayangan Tingkat Tinggi, sekolah terbaik di seluruh Klan Bulan. Tentang persahabatan tiga mahasiswa, yang diam-diam memiliki rencana bertualang ke tempat-tempat jauh. Tapi petualangan itu berakhir buruk, saat persahabatan mereka diuji dengan rasa suka, egoisme, dan pengkhianatan. Ada banyak karakter baru, tempat-tempat baru, juga sejarah dunia paralel yang diungkap. Di dua buku ini kalian akan berkenalan dengan salah satu karakter paling kuat di dunia paralel sejauh ini. Tapi itu jika kalian bisa menebaknya. Dua buku ini bukan akhir. Justru awal terbukanya kembali portal menuju Klan Aldebaran.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.nebula
        ));
        bookList.add(new Book("Tentang Kamu", "Tere Liye", "2021",
                "\"Terima kasih untuk kesempatan mengenalmu, itu adalah salah satu anugerah terbesar hidupku. Cinta memang tidak perlu ditemukan, cintalah yang akan menemukan kita.\"\n" +
                        "\n" +
                        "\"Nasihat lama itu benar sekali, aku tidak akan menangis karena sesuatu telah berakhir, tapi aku akan tersenyum karena sesuatu itu pernah terjadi.\"\n" +
                        "\n" +
                        "\"Masa lalu. Rasa sakit. Masa depan. Mimpi-mimpi. Semua akan berlalu, seperti sungai yang mengalir. Maka biarlah hidupku mengalir seperti sungai kehidupan.\"",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.tentang_kamu
        ));
        bookList.add(new Book("Harry Potter and The Prisoner of Azkaban", "J.K. Rowling", "1999",
                "Harry Potter, seorang penyihir muda yang terkenal karena bertahan hidup dari serangan mematikan Lord Voldemort, kembali ke sekolah sihir Hogwarts untuk tahun ketiganya. Namun, tahun ini lebih berbahaya daripada yang pernah ia bayangkan. Seorang tahanan dari penjara Azkaban, Sirius Black, melarikan diri dan diyakini sedang mencari Harry. Bersama teman-temannya, Ron Weasley dan Hermione Granger, Harry harus mengungkap misteri tentang Black, yang ternyata memiliki hubungan dengan masa lalu keluarga Potter.",
                false,
                "android.resource://" + context.getPackageName() + "/" + R.drawable.hp_the_prisoner_of_azkaban
        ));
    }


    public static void addBook(Book book) {
        bookList.add(0, book);
    }

    public static void clearBooks() {
        bookList.clear();
    }

    public static Object getBooks() {
        return bookList;
    }

    public static void addFavorite(Book book) {
        if (!favoriteBooks.contains(book)) {
            favoriteBooks.add(0, book);
            book.setLike(true);
        }
    }

    public static void removeFavorite(Book book) {
        favoriteBooks.remove(book);
        book.setLike(false);
    }


    public static List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public static boolean isFavorite(Book book) {
        return favoriteBooks.contains(book);
    }
}

