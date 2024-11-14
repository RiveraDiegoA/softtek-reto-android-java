# Proyecto Android - Aplicación de Películas

## Descripción del Proyecto

Este proyecto es una aplicación de Android desarrollada en **Java**, utilizando el patrón de diseño
**MVC (Modelo-Vista-Controlador)**. La aplicación permite a los usuarios explorar y ver detalles de
una variedad de películas. La implementación está basada en una arquitectura que separa claramente
la lógica de negocio, la interfaz de usuario y el control de flujo, asegurando un código más
mantenible y modular.

## Tecnologías Utilizadas

- **Java**: Lenguaje principal de programación para el desarrollo del proyecto.
- **Android SDK**: Herramientas de desarrollo y API de Android.
- **MVC (Modelo-Vista-Controlador)**: Patrón arquitectónico que separa la lógica de negocio, la
  interfaz de usuario y el controlador.
- **Room**: Para la persistencia de datos locales.
- **Retrofit**: Para el consumo de APIs REST.
- **ViewBinding y DataBinding**: Para simplificar el código de las vistas y mejorar la vinculación
  de datos.

## Estructura del Proyecto

El proyecto sigue una estructura basada en el patrón **MVC**, donde cada componente tiene un rol
claramente definido:

### 1. Modelo (Model)

La capa del modelo se encarga de manejar la lógica de negocio y el acceso a los datos. Contiene:

- **Entidades**: Clases que representan los objetos de negocio, como `Movie`.
- **Repositorio**: Clases que se encargan de interactuar con la base de datos local (Room) o con
  APIs remotas (Retrofit).

**Ubicación**: `com.riveracompany.peliculasapp_sofftek_retoandroid.model`

### 2. Vista (View)

La capa de vista se encarga de la interfaz de usuario. Incluye:

- **Activities** y **Fragments** que presentan la información al usuario.
- **Adapters** para RecyclerViews que muestran listas de películas.

**Ubicación**: `com.riveracompany.peliculasapp_sofftek_retoandroid.view`

### 3. Controlador (Controller)

El controlador gestiona la lógica de presentación y actúa como intermediario entre la vista y el
modelo. Se encarga de manejar la interacción del usuario y actualizar la vista en consecuencia.

**Ubicación**: `com.riveracompany.peliculasapp_sofftek_retoandroid.controller`

### 4. Callback (Callback)

Este paquete contiene las interfaces de callback que se utilizan para la comunicación entre
diferentes componentes de la aplicación, facilitando la reutilización y el desacoplamiento.

**Ubicación**: `com.riveracompany.peliculasapp_sofftek_retoandroid.callback`

## Estructura de Carpetas

```
com.riveracompany.peliculasapp_sofftek_retoandroid/
    ├── model/
    │   ├── entities/
    │   │   └── Movie.java
    │   ├── repository/
    │   │   └── MovieRepository.java
    │
    ├── view/
    │   ├── activities/
    │   │   ├── SplashScreenActivity.java
    │   │   ├── LoginActivity.java
    │   │   └── MovieListActivity.java
    │   ├── fragments/
    │   │   └── MovieDetailFragment.java
    │   ├── adapters/
    │   │   └── MovieListAdapter.java
    │
    ├── controller/
    │   └── MovieController.java
    │
    ├── callback/
    │   └── LoginResultCallback.java
    │
    └── utils/
        └── Utils.java
```

## Características Principales

- **Listado de Películas**: Muestra una lista de películas obtenidas a través de una API.
- **Detalles de la Película**: Permite ver información detallada sobre cada película.
- **Animaciones**: Utiliza animaciones para mejorar la experiencia del usuario.
- **Manejo de Estado Offline**: Capacidad de mostrar información cuando no hay conexión a internet
  mediante Room.

## Instalación

1. **Clonar el Repositorio**
   ```sh
   git clone https://github.com/usuario/proyecto-android-peliculas.git
   ```

2. **Abrir en Android Studio**
    - Abre Android Studio y selecciona "Open an Existing Project".
    - Navega hasta la carpeta donde clonaste el repositorio.

3. **Configurar las Dependencias**
    - Android Studio descargará las dependencias necesarias (Room, Retrofit, etc.) de forma
      automática.

4. **Ejecutar la Aplicación**
    - Conecta un dispositivo Android o utiliza un emulador para ejecutar la aplicación.

## Uso de la Aplicación

- Al abrir la aplicación, se muestra una **pantalla de inicio** con el logo animado.
- Después de la animación, el usuario es dirigido a la **pantalla de Login**.
- Tras iniciar sesión, se muestra una lista de películas disponibles.
- Al seleccionar una película, se despliega la **pantalla de detalles** con información adicional.

## Ejemplo de Código

A continuación se muestra un ejemplo de cómo se maneja la autenticación en el módulo de inicio de
sesión (`LoginActivity`) usando una interfaz `LoginResultCallback` y el patrón MVC:

```java
public class LoginActivity extends AppCompatActivity implements LoginResultCallback {
    private static final int VALIDATION_VIEW_DURATION = 2500; // 2.5 seconds
    private ActivityLoginBinding binding;
    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a_lgn_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        controller = new LoginController(this, this);
        initializeView();
    }

    private void initializeView() {
        // Manejar la entrada del usuario en los campos de texto
        controller.utils.handleEditText(binding.aLgnEdtUsername, binding.aLgnTilUsername);
        controller.utils.handleEditText(binding.aLgnEdtPassword, binding.aLgnTilPassword);

        // Configurar el botón de inicio de sesión
        binding.aLgnBtnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.aLgnEdtUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.aLgnEdtPassword.getText()).toString();
            controller.handleLogin(username, password);
        });
    }

    @Override
    public void onLoginStart() {
        // Mostrar el estado de validación al iniciar sesión
        handleValidateView(R.drawable.ic_search_activity, getString(R.string.login_validate), getString(R.string.login_validate_desc), true);
        binding.aLgnLytContent.setVisibility(View.GONE);
        binding.aLgnLytValidate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess() {
        // Mostrar el estado de éxito y comenzar la transición
        handleValidateView(R.drawable.ic_verified_user, getString(R.string.login_success), getString(R.string.login_success_desc), false);
        controller.startTransition();
    }

    @Override
    public void onLoginError(boolean isUsername, boolean isPassword, String message) {
        // Manejar errores en el inicio de sesión
        if (!isUsername)
            controller.utils.handleEditTextError(binding.aLgnEdtUsername, binding.aLgnTilUsername, message);
        else if (!isPassword)
            controller.utils.handleEditTextError(binding.aLgnEdtPassword, binding.aLgnTilPassword, message);
        else {
            handleValidateView(R.drawable.ic_error, getString(R.string.login_error), message, false);
            new Handler().postDelayed(() -> {
                binding.aLgnLytValidate.setVisibility(View.GONE);
                binding.aLgnLytContent.setVisibility(View.VISIBLE);
            }, VALIDATION_VIEW_DURATION);
        }
    }

    private void handleValidateView(int icon, String title, String desc, boolean isVisibleProgressBar) {
        // Actualizar la vista de validación con los detalles proporcionados
        binding.aLgnLytValidateImv.setImageResource(icon);
        binding.aLgnLytValidateTxvTitle.setText(title);
        binding.aLgnLytValidateTxvDesc.setText(desc);
        binding.aLgnLytValidatePb.setVisibility(isVisibleProgressBar ? View.VISIBLE : View.INVISIBLE);
    }
}
```

## Contribución

Las contribuciones son bienvenidas. Para contribuir, sigue los siguientes pasos:

1. **Fork** del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz un commit (`git commit -m 'Agregar nueva funcionalidad'`).
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un **Pull Request**.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Para más información, consulta el
archivo [LICENSE](LICENSE).

## Contacto

- **Nombre del Desarrollador**: [Diego Rivera A]
- **Email**: [diegorivera.a04@gmail.com]
- **GitHub**: [https://github.com/RiveraDiegoA]