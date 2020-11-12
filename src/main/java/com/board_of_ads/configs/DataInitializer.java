package com.board_of_ads.configs;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.Message;
import com.board_of_ads.models.Notification;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.service.interfaces.MessageService;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.service.interfaces.KladrService;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final KladrService kladrService;
    private final CategoryService categoryService;
    private final PostingService postingService;
    private final CityService cityService;
    private final ImageService imageService;
    private final NotificationService notificationService;
    private final MessageService messageService;


    @PostConstruct
    private void init() throws IOException {
        initUsers();
        initMoreUsers();
        initKladr();
        initUsers();
        initCategories();
        initPosting();
        initNotifications();
        initMessages();
    }

    private void initUsers() {

        if (roleService.getRoleByName("ADMIN") == null) {
            roleService.saveRole(new Role("ADMIN"));
        }
        if (roleService.getRoleByName("USER") == null) {
            roleService.saveRole(new Role("USER"));
        }
        if (userService.getUserByEmail("admin@mail.ru") == null) {
            User admin = new User();
            admin.setEmail("admin@mail.ru");
            admin.setPassword("1234567");
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setPhone("8-922-0123456");
            admin.setAvatar(new Image(null, "images/admin.jpg"));
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(roleService.getRoleByName("ADMIN"));
            admin.setRoles(roleAdmin);
            // admin.setCity(cityService.findCityByName("Екатеринбург").get());
            userService.saveUser(admin);
        }
        if (userService.getUserByEmail("user@mail.ru") == null) {
            User user = new User();
            user.setEmail("user@mail.ru");
            user.setPassword("1234567");
            user.setFirstName("User");
            user.setLastName("User");
            user.setPhone("8-922-1234567");
            user.setAvatar(new Image(null, "images/user.jpg"));
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(roleService.getRoleByName("USER"));
            user.setRoles(roleAdmin);
            // user.setCity(cityService.findCityByName("Рязань").get());
            userService.saveUser(user);
        }
    }

    private void initCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Транспорт", null, 1));
        categoryList.add(new Category("Недвижимость", null, 1));
        categoryList.add(new Category("Работа", null, 1));
        categoryList.add(new Category("Услуги", null, 1));
        categoryList.add(new Category("Личные вещи", null, 1));
        categoryList.add(new Category("Для дома и дачи", null, 1));
        categoryList.add(new Category("Бытовая электроника", null, 1));
        categoryList.add(new Category("Хобби и отдых", null, 1));
        categoryList.add(new Category("Животные", null, 1));
        categoryList.add(new Category("Для бизнеса", null, 1));

        for (Category category : categoryList) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }


        List<Category> subCategoryList = new ArrayList<>();
        subCategoryList.add(new Category("Автомобили", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Мотоциклы и мототехника", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Грузовики и спецтранспорт", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Водный транспорт", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Запчасти и автоаксессуары", categoryService.getCategoryByName("Транспорт").get(), 2));

        subCategoryList.add(new Category("Квартиры", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Комнаты", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Дома, дачи, коттеджи", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Гаражи и машиноместа", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Земельные участки", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Коммерческая недвижимость", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Недвижимость за рубежом", categoryService.getCategoryByName("Недвижимость").get(), 2));

        subCategoryList.add(new Category("Вакансии", categoryService.getCategoryByName("Работа").get(), 2));
        subCategoryList.add(new Category("Резюме", categoryService.getCategoryByName("Работа").get(), 2));

        subCategoryList.add(new Category("Одежда, обувь, аксессуары", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Детская одежда и обувь", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Товары для детей и игрушки", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Часы и украшения", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Красота и здоровье", categoryService.getCategoryByName("Личные вещи").get(), 2));

        subCategoryList.add(new Category("Бытовая техника", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Мебель и интерьер", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Посуда и товары для кухни", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Продукты питания", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Ремонт и строительство", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Растения", categoryService.getCategoryByName("Для дома и дачи").get(), 2));

        subCategoryList.add(new Category("Аудио и видео", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Игры, приставки и программы", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Настольные компьютеры", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Ноутбуки", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Оргтехника и расходники", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Планшеты и электронные книги", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Телефоны", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Товары для компьютера", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Фототехника", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Билеты и путешествия", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Велосипеды", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Книги и журналы", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Коллекционирование", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Музыкальные инструменты", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Охота и рыбалка", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Спорт и отдых", categoryService.getCategoryByName("Хобби и отдых").get(), 2));

        subCategoryList.add(new Category("Собаки", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Кошки", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Птицы", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Аквариум", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Другие животные", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Товары для животных", categoryService.getCategoryByName("Животные").get(), 2));

        subCategoryList.add(new Category("Готовый бизнес", categoryService.getCategoryByName("Для бизнеса").get(), 2));
        subCategoryList.add(new Category("Оборудование для бизнеса", categoryService.getCategoryByName("Для бизнеса").get(), 2));

        for (Category category : subCategoryList) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }


        List<Category> secondSubCategory = new ArrayList<>();
        Category myUsedCarCategory = new Category("С пробегом", categoryService.getCategoryByName("Автомобили").get(), 3, "used-car");
        Category myNewCarCategory = new Category("Новые", categoryService.getCategoryByName("Автомобили").get(), 3, "new-car");

//        secondSubCategory.add(new Category("С пробегом", categoryService.getCategoryByName("Транспорт:Автомобили").get(), 3));
//        secondSubCategory.add(new Category("Новые", categoryService.getCategoryByName("Транспорт:Автомобили").get(), 3));

        secondSubCategory.add(myNewCarCategory);
        secondSubCategory.add(myUsedCarCategory);

        secondSubCategory.add(new Category("Багги", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Вездеходы", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Картинг", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Квадроциклы", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Мопеды и скутеры", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Мотоциклы", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Снегоходы", categoryService.getCategoryByName("Мотоциклы и мототехника").get(), 3));

        secondSubCategory.add(new Category("Автобусы", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Автодома", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Автокраны", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Бульдозеры", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Грузовики", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Коммунальная техника", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Легкий транспорт", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Погрузчики", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Прицепы", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Сельхозтехникам", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Строительная техника", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Техника для лесозаготовки", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Тягачи", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Эскаваторы", categoryService.getCategoryByName("Грузовики и спецтранспорт").get(), 3));

        secondSubCategory.add(new Category("Вёсельные лодки", categoryService.getCategoryByName("Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Гидроциклы", categoryService.getCategoryByName("Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Катера и яхты", categoryService.getCategoryByName("Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Каяки и каноэ", categoryService.getCategoryByName("Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Моторные лодки", categoryService.getCategoryByName("Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Надувные лодки", categoryService.getCategoryByName("Водный транспорт").get(), 3));

        secondSubCategory.add(new Category("Запчасти", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("GPS-навигаторы", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Автокосметика и автохимия", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Аудио и видеотехника", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Багажники и фаркопы", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Инструменты", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Прицепы", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Противоугонные устройства", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Тюнинг", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Шины, диски и колеса", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Экипировка", categoryService.getCategoryByName("Запчасти и автоаксессуары").get(), 3));


        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Квартиры").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Квартиры").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Квартиры").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Квартиры").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Комнаты").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Комнаты").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Комнаты").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Комнаты").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Дома, дачи, коттеджи").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Гаражи и машиноместа").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Земельные участки").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Коммерческая недвижимость").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость за рубежом").get(), 3));


        secondSubCategory.add(new Category("IT, интернет, телеком", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Автомобильный бизнес", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Административная работа", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Банки, инвестиции", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Без опыта, студенты", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Бухгалтерия, финансы", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Высший менеджмент", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Госслужба, НКО", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Домашний персонал", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("ЖКХ, эксплуатация", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Исскуство, развлечения", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Консультирование", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Маркетинг, реклама, PR", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Медицина, фармацевтика", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Образование, наука", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Охрана, безопасность", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Продажи", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Производство, сырьё, с/х", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Страхование", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Транспорт, логистика", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Туризм, рестораны", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Управление персоналом", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Фитнес, салоны красоты", categoryService.getCategoryByName("Вакансии").get(), 3));
        secondSubCategory.add(new Category("Юриспруденция", categoryService.getCategoryByName("Вакансии").get(), 3));

        secondSubCategory.add(new Category("IT, интернет, телеком", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Автомобильный бизнес", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Административная работа", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Банки, инвестиции", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Без опыта, студенты", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Бухгалтерия, финансы", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Высший менеджмент", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Госслужба, НКО", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Домашний персонал", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("ЖКХ, эксплуатация", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Исскуство, развлечения", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Консультирование", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Маркетинг, реклама, PR", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Медицина, фармацевтика", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Образование, наука", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Охрана, безопасность", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Продажи", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Производство, сырьё, с/х", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Страхование", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Транспорт, логистика", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Туризм, рестораны", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Управление персоналом", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Фитнес, салоны красоты", categoryService.getCategoryByName("Резюме").get(), 3));
        secondSubCategory.add(new Category("Юриспруденция", categoryService.getCategoryByName("Резюме").get(), 3));


        secondSubCategory.add(new Category("Женская одежда", categoryService.getCategoryByName("Одежда, обувь, аксессуары").get(), 3));
        secondSubCategory.add(new Category("Мужская одежда", categoryService.getCategoryByName("Одежда, обувь, аксессуары").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Одежда, обувь, аксессуары").get(), 3));

        secondSubCategory.add(new Category("Для девочек", categoryService.getCategoryByName("Детская одежда и обувь").get(), 3));
        secondSubCategory.add(new Category("Для мальчиков", categoryService.getCategoryByName("Детская одежда и обувь").get(), 3));

        secondSubCategory.add(new Category("Автомобильные кресла", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Велосипеды и самокаты", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Детская мебель", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Детские коляски", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Игрушки", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Постельные принадлежности", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для кормления", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для купания", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для школы", categoryService.getCategoryByName("Товары для детей и игрушки").get(), 3));

        secondSubCategory.add(new Category("Бижутерия", categoryService.getCategoryByName("Часы и украшения").get(), 3));
        secondSubCategory.add(new Category("Часы", categoryService.getCategoryByName("Часы и украшения").get(), 3));
        secondSubCategory.add(new Category("Ювелирные изделия", categoryService.getCategoryByName("Часы и украшения").get(), 3));

        secondSubCategory.add(new Category("Косметика", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Парфюмерия", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Приборы и аксессуары", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Средства гигиены", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Средства для волос", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Медицинские изделия", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Биологически активные добавки", categoryService.getCategoryByName("Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Услуги", categoryService.getCategoryByName("Красота и здоровье").get(), 3));


        secondSubCategory.add(new Category("Для дома", categoryService.getCategoryByName("Бытовая техника").get(), 3, "householdAppliances"));
        secondSubCategory.add(new Category("Для индивидуального ухода", categoryService.getCategoryByName("Бытовая техника").get(), 3, "householdAppliances"));
        secondSubCategory.add(new Category("Для кухни", categoryService.getCategoryByName("Бытовая техника").get(), 3, "householdAppliances"));
        secondSubCategory.add(new Category("Климатическое оборудование", categoryService.getCategoryByName("Бытовая техника").get(), 3, "householdAppliances"));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Бытовая техника").get(), 3, "householdAppliances"));

        secondSubCategory.add(new Category("Компьютерные столы и кресла", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Кровати, диваны и кресла", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Кухонные гарнитуры", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Освещение", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Подставки и тумбы", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Предметы интерьера, искусство", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Столы и стулья", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Текстиль и ковры", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Шкафы и комоды", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Мебель и интерьер").get(), 3));

        secondSubCategory.add(new Category("Посуда", categoryService.getCategoryByName("Посуда и товары для кухни").get(), 3));
        secondSubCategory.add(new Category("Товары для кухни", categoryService.getCategoryByName("Посуда и товары для кухни").get(), 3));

        secondSubCategory.add(new Category("Двери", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Инструменты", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Камины и обогреватели", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Окна и балконы", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Потолки", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Садовая техника", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Сантехника и сауна", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Стройматериалы", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Услуги", categoryService.getCategoryByName("Ремонт и строительство").get(), 3));


        secondSubCategory.add(new Category("MP3 плееры", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Акустика, колонки, сабвуферы", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Видео, DVD и Blu-Ray плееры", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Видеокамеры", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Кабели и адаптеры", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Микрофоны", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Музыка и фильмы", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Музыкальные центры, магнитолы", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Наушники", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Телевизоры и проекторы", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Усилители и ресиверы", categoryService.getCategoryByName("Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Аудио и видео").get(), 3));

        secondSubCategory.add(new Category("Игры для приставок", categoryService.getCategoryByName("Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Игровые приставки", categoryService.getCategoryByName("Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Компьютерные игры", categoryService.getCategoryByName("Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Программы", categoryService.getCategoryByName("Игры, приставки и программы").get(), 3));

        secondSubCategory.add(new Category("МФУ, копиры и сканнеры", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Принтеры", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Телефония", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("ИБП, сетевые фильтры", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Уничтожители бумаг", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Расходные материалы", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Канцелярия", categoryService.getCategoryByName("Оргтехника и расходники").get(), 3));

        secondSubCategory.add(new Category("Планшеты", categoryService.getCategoryByName("Планшеты и электронные книги").get(), 3));
        secondSubCategory.add(new Category("Электронные книги", categoryService.getCategoryByName("Планшеты и электронные книги").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Планшеты и электронные книги").get(), 3));

        secondSubCategory.add(new Category("Акустика", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Веб-камеры", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Джойстики и руль", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Клавиатуры и мыши", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Комплектующее", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Мониторы", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Переносные жёсткие диски", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Сетевое оборудование", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("ТВ-тюнеры", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Флешки и карты памяти", categoryService.getCategoryByName("Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Акксессуары", categoryService.getCategoryByName("Товары для компьютера").get(), 3));

        secondSubCategory.add(new Category("Компактные фотоаппараты", categoryService.getCategoryByName("Фототехника").get(), 3));
        secondSubCategory.add(new Category("Зеркальные фотоаппараты", categoryService.getCategoryByName("Фототехника").get(), 3));
        secondSubCategory.add(new Category("Пленочные фотоаппараты", categoryService.getCategoryByName("Фототехника").get(), 3));
        secondSubCategory.add(new Category("Бинокли и телескопы", categoryService.getCategoryByName("Фототехника").get(), 3));
        secondSubCategory.add(new Category("Объективы", categoryService.getCategoryByName("Фототехника").get(), 3));
        secondSubCategory.add(new Category("Оборудование и аксессуары", categoryService.getCategoryByName("Фототехника").get(), 3));


        secondSubCategory.add(new Category("Карты, купоны", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Концерты", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Путешествия", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Спорт", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Театр, опера, балет", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Цирк, кино", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Шоу, мюзикл", categoryService.getCategoryByName("Билеты и путешествия").get(), 3));

        secondSubCategory.add(new Category("Горные", categoryService.getCategoryByName("Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Дорожные", categoryService.getCategoryByName("Велосипеды").get(), 3));
        secondSubCategory.add(new Category("BMX", categoryService.getCategoryByName("Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Детские", categoryService.getCategoryByName("Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Запчасти и аксессуары", categoryService.getCategoryByName("Велосипеды").get(), 3));

        secondSubCategory.add(new Category("Журналы, газеты, брошюры", categoryService.getCategoryByName("Книги и журналы").get(), 3));
        secondSubCategory.add(new Category("Книги", categoryService.getCategoryByName("Книги и журналы").get(), 3));
        secondSubCategory.add(new Category("Учебная литература", categoryService.getCategoryByName("Книги и журналы").get(), 3));

        secondSubCategory.add(new Category("Банкноты", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Билеты", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Вещи знаменитостей, автографы", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Военные вещи", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Грампластинки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Документы", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Жетоны, медали, значки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Игры", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Календари", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Картины", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Киндер-Сюрприз", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Конверты и почтовые карточки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Макеты оружия", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Марки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Модели", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Монеты", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Открытки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Пепельницы, зажигалки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Пластиковые карточки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Спортивные карточки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Фотографии, письма", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Этикетки, бутылки, пробки", categoryService.getCategoryByName("Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Коллекционирование").get(), 3));

        secondSubCategory.add(new Category("Аккордеоны, гармонии, баяны", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Гитары и другие струнные", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Духовные", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Пианино и другие клавишные", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Скрипки и другие смычковые", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Ударные", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Для студии и концертов", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Музыкальные инструменты").get(), 3));

        secondSubCategory.add(new Category("Бильярд и боулинг", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Дайвинг и водный спорт", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Единоборства", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Зимние виды спорта", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Игры с мячом", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Настольные игры", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Пейнтбол и страйкбол", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Ролики и скейтбординг", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Теннис, бадминтон, пинг-понг", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Туризм", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Финтес и тренажеры", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Спортивное питание", categoryService.getCategoryByName("Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Спорт и отдых").get(), 3));


        secondSubCategory.add(new Category("Интернет-магазин", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Общественное питание", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Производство", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Развлечение", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Сельское хозяйство", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Сфера услуг", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Торговля", categoryService.getCategoryByName("Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Готовый бизнес").get(), 3));

        secondSubCategory.add(new Category("Для магазина", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для офиса", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для ресторана", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для салона красоты", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Промышленное", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Оборудование для бизнеса").get(), 3));

        for (Category category : secondSubCategory) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }
    }

    private void initKladr() throws IOException {
        kladrService.streamKladr();
    }

    private void initPosting() {
        List<Posting> postingList = new ArrayList<>();
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Поглажу кота", "Очень качественно", 100L, "+79998887766", cityService.findCityByName("Ростов").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Поддержу советом", "Не факт что полезным", 50L, "+79998887766", cityService.findCityByName("Ростов").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Ремонт электроники", "Быстро, качественно", 1000L, "+79998887766", cityService.findCityByName("Ростов").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Монтаж электросетей", "Любая сложность", 10_000L, "+79998887766", cityService.findCityByName("Азов").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Няня", "от 1 года", 2_000L, "+79998887766", cityService.findCityByName("Азов").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Посмотрю телевизор за Вас", "только 16к", 1_000L, "+79998887766", cityService.findCityByName("Азов").get(), false, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Схожу за продуктами", "Могу в Ашан, могу в Пятерочку", 1_000L, "+79998887766", cityService.findCityByName("Азов").get(), false, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Выгуляю собаку", "Ей понравится", 1_000L, "+79998887766", cityService.findCityByName("Азов").get(), false, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Газовщик", "Любая сложность", 2_000L, "+79998887766", cityService.findCityByName("Азов").get(), false, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Врач", "Терапевт", 3_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Стоматолог", "Будет не больно", 5_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Киллер", "Будет больно", 300_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Собутыльник", "Будет весело", 500L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Грузовые перевозки", "Трезвые грузчики", 10_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сыграю в лото", "Я в этом хорош", 500L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Разобью сердце", "Только парням", 10_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Схожу в кино", "За компанию", 1_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get(), true, 0));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сдамся в рабство", "ненадолго", 50_000L, "+79998887766", true));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Уведу у Вас девушку", "Вдруг она вам надоела", 3_000L, "+79998887766", false));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Обижу обидчиков", "Не старше 18 лет", 5_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Отмажу от ментов", "У меня папка начальник", 10_000L, "+79896661488", false));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Вынесу мусор", "Небольшой", 400L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Научу играть на гитаре", "Учил самого Цоя", 10_000L, "+79896661488", false));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Научу играть в Warcraft", "PvP или зассал?", 10_000L, "+79896661488", false));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Продам средство для похудения", "Результат уже через 3 дня. Нужно всего лишь 1 ложка...", 10_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Продам ядерный реактор", "Самовывоз с Припяти", 500_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Стань программистом за 1 урок", "Урок №1: перезагрузка роутера", 20_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Свадебный фотограф", "Фоткаю на iPhone 7", 10_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Компьютерный мастер на дом", "Перезагружу ваш компьютер быстро и качественно", 4_000L, "+79896661488", true));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сесть на пенёк", "...Почему тут так мало?", 1_000L, "+79896661488", true));

        Image one = new Image(null, "/images/numbers/0.jpg");
        Image two = new Image(null, "/images/numbers/1.jpg");
        Image three = new Image(null, "/images/numbers/2.jpg");
        Image four = new Image(null, "/images/numbers/3.jpg");
        Image five = new Image(null, "/images/numbers/4.jpg");

        imageService.save(one);
        imageService.save(two);
        imageService.save(three);
        imageService.save(four);
        imageService.save(five);

        List<Image> imageList = new ArrayList<>();
        imageList.add(imageService.getByPathURL("/images/numbers/0.jpg"));
        imageList.add(imageService.getByPathURL("/images/numbers/1.jpg"));
        imageList.add(imageService.getByPathURL("/images/numbers/2.jpg"));
        imageList.add(imageService.getByPathURL("/images/numbers/3.jpg"));
        imageList.add(imageService.getByPathURL("/images/numbers/4.jpg"));

        for (Posting posting : postingList) {
            if (postingService.getPostingByTitle(posting.getTitle()).isEmpty()) {
                posting.setImages(imageList);
                postingService.save(posting);
            }
        }
    }

    private void initMoreUsers() {

        if (userService.getUserByEmail("super@mail.ru") == null) {
            User admin = new User();
            admin.setEmail("super@mail.ru");
            admin.setPassword("super");
            admin.setFirstName("Super");
            admin.setLastName("Mario");
            admin.setAvatar(new Image(null, "images/admin.jpg"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleByName("ADMIN"));
            admin.setRoles(roles);
            userService.saveUser(admin);
        }

        if (userService.getUserByEmail("simple@mail.ru") == null) {
            User user = new User();
            user.setEmail("simple@mail.ru");
            user.setPassword("1111111");
            user.setFirstName("Simply");
            user.setLastName("Red");
            user.setAvatar(new Image(null, "images/user.jpg"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleByName("ADMIN"));
            user.setRoles(roles);
            userService.saveUser(user);
        }

        if (userService.getUserByEmail("hero@mail.ru") == null) {
            User user = new User();
            user.setEmail("hero@mail.ru");
            user.setPassword("1111111");
            user.setFirstName("Hero");
            user.setLastName("Sensei");
            user.setAvatar(new Image(null, "images/user.jpg"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleByName("USER"));
            user.setRoles(roles);
            userService.saveUser(user);
        }
    }

    private void initNotifications() {
        List<User> groupOfAllUsers = userService.getAllUsers();
        Long adminId = userService.getUserByEmail("admin@mail.ru").getId();
        Long simplyUserId = userService.getUserByEmail("user@mail.ru").getId();
        List<User> singleUser = Collections.singletonList(userService.getUserById(adminId));
        List<User> singleUser2 = Collections.singletonList(userService.getUserById(simplyUserId));
        Notification notification1 = new Notification("Это вообще первое уведомление",
                "Привет! Раз ты читаешь это уведомление, наверное ты в команде проекта board of ads. Ты находишься в разделе уведомлений");
        Notification notification2 = new Notification("Что мы делаем?",
                "Делаем клон Avito. Проект полу-настоящий, венчурная внутренняя разработка JM силами " +
                        "студентов на проекте. Задача – самостоятельно взаимодействовать в командной работе с " +
                        "минимальным участием ментора");
        Notification notification3 = new Notification("Какой javascript задействован в разделе уведомления?",
                "Логика управления уведомлениями зашита в скрипте profile_notes.js." +
                        " Смотри resources/static/js");
        Notification notification4 = new Notification("Что получаем в response?",
                "При fetch запросе получаем DTO сущность NotificationDTO с полями notificationId, " +
                        "messageTitle, messageBody, clickAction, sentTime, status, urgentLevel");
        Notification notification5 = new Notification("Как устроены Rest-запросы на уведомления?",
                "Тестируешь раздел уведомлений? Запросы отправляются в Rest-контроллер NotificationRestController");
        notification2.setClickAction("https://www.google.com");
        notification4.setClickAction("https://learn.javascript.ru/fetch");

        notificationService.createNotification(notification1);
        notificationService.createNotification(notification2);
        notificationService.createNotification(notification3);
        notificationService.createNotification(notification4);
        notificationService.createNotification(notification5);

        notificationService.sendNotificationToUsers(notification1, singleUser);
        notificationService.sendNotificationToUsers(notification2, singleUser);
        notificationService.sendNotificationToUsers(notification3, singleUser);
        notificationService.sendNotificationToUsers(notification3, singleUser2);

        notificationService.sendNotificationToUsers(notification4, singleUser);
        notificationService.sendNotificationToUsers(notification5, groupOfAllUsers);
        notificationService.sendNotificationToUsers(notification3, singleUser2);

        UserNotification un = notificationService.findByNoteIdAndUserId(1696L, adminId);
        if (un != null) {
            un.setStatus("read");
            notificationService.updateUserNotificationFields(un);
        }

        UserNotification un2 = notificationService.findByNoteIdAndUserId(1694L, adminId);
        if (un2 != null) {
            un2.setUrgentLevel(2);
            notificationService.updateUserNotificationFields(un2);
        }
    }
    private void initMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "привет", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1600L)));
        messages.add(new Message(2L, "можно обсудить скидку?", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1601L)));
        messages.add(new Message(4L, "здравствуйте", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1602L)));
        messages.add(new Message(5L, "еще актуально?", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1603L)));
        messages.add(new Message(6L, "приеду завтра", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1604L)));
        messages.add(new Message(7L, "подумаю", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1605L)));
        messages.add(new Message(8L, "предложу другу", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1606L)));
        messages.add(new Message(9L, "приеду сегодня вечером", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1607L)));
        messages.add(new Message(10L, "спасибо!", userService.getUserByEmail("user@mail.ru"), postingService.getPostingById(1608L)));

        for (Message message : messages) {
            messageService.save(message);
        }
    }
}
