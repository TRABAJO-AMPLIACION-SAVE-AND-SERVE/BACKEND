package com.example.saveandserve.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.saveandserve.demo.entity.Alergenos;
import com.example.saveandserve.demo.entity.Articulo;
import com.example.saveandserve.demo.entity.BancoDeAlimentos;
import com.example.saveandserve.demo.entity.Empresa;
import com.example.saveandserve.demo.entity.Empresa.Suscripcion;
import com.example.saveandserve.demo.entity.TipoTransporte;
import com.example.saveandserve.demo.entity.Transporte;
import com.example.saveandserve.demo.entity.Usuario;
import com.example.saveandserve.demo.entity.UsuarioRol;
import com.example.saveandserve.demo.repository.AlergenosRepository;
import com.example.saveandserve.demo.repository.ArticuloRepository;
import com.example.saveandserve.demo.repository.BancoDeAlimentosRepository;
import com.example.saveandserve.demo.repository.DonacionRepository;
import com.example.saveandserve.demo.repository.EmpresaRepository;
import com.example.saveandserve.demo.repository.TransporteRepository;
import com.example.saveandserve.demo.repository.UsuarioRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EmpresaRepository empresaRepository,
            BancoDeAlimentosRepository bancoDeAlimentosRepository, DonacionRepository donacionRepository,
            AlergenosRepository alergenosRepository, TransporteRepository transporteRepository,
            BCryptPasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,
            ArticuloRepository articulosRepository) {
        return (args) -> {
            if (empresaRepository.count() == 0) {
                List<Empresa> empresas = Arrays.asList(
                        new Empresa(null, "SaborArte Catering", "contacto@saborartecatering.com",
                                "Explanada de España, 1, 03002", "123-456-7890", "CIF12345678",
                                passwordEncoder.encode("1234"), "Tipo1", "Alicante", Suscripcion.ESTANDAR, null),
                        new Empresa(null, "Delicia Express", "pedidos@deliciaexpress.com", "Gran Vía, 28, 28013",
                                "123-456-7891", "CIF12345679", passwordEncoder.encode("1234"), "Tipo2", "Madrid",
                                Suscripcion.BASICA, null),
                        new Empresa(null, "La Cava Bistró", "contacto@lacavabistro.com", "Passeig de Gràcia, 92, 08008",
                                "123-456-7892", "CIF12345680", passwordEncoder.encode("1234"), "Tipo3", "Barcelona",
                                Suscripcion.PREMIUM, null),
                        new Empresa(null, "Verde Sano Foods", "info@verdesanofoods.com", "Calle de la Paz, 48, 46003",
                                "123-456-7893", "CIF12345681", passwordEncoder.encode("1234"), "Tipo4", "Valencia",
                                Suscripcion.BASICA, null),
                        new Empresa(null, "Brasa & Gril", "atencion@brasaandgrill.com",
                                "Avenida de la Constitución, 21, 41004", "123-456-7894", "CIF12345682",
                                passwordEncoder.encode("1234"), "Tipo5", "Sevilla", Suscripcion.BASICA, null),
                        new Empresa(null, "Dulce Tentación Bakery", "ventas@dulcetentacionbakery.com",
                                "Calle Larios, 10, 29005", "123-456-7895", "CIF12345683",
                                passwordEncoder.encode("1234"), "Tipo6", "Málaga", Suscripcion.BASICA, null),
                        new Empresa(null, "Pura Vida Juice Bar", "hola@puravidajuicebar.com",
                                "Gran Vía del Escultor Salzillo, 7, 30004", "123-456-7896", "CIF12345684",
                                passwordEncoder.encode("1234"), "Tipo7", "Murcia", Suscripcion.PREMIUM, null),
                        new Empresa(null, "Gourmet Urbano", "contacto@gourmeturbano.com", "Plaza Moyúa, 4, 48009",
                                "123-456-7897", "CIF12345685", passwordEncoder.encode("1234"), "Tipo8", "Bilbao",
                                Suscripcion.PREMIUM, null),
                        new Empresa(null, "La Mesa Italiana", "reservas@lamesaitaliana.com",
                                "Paseo de la Independencia, 12, 50004", "123-456-7898", "CIF12345686",
                                passwordEncoder.encode("1234"), "Tipo9", "Zaragoza", Suscripcion.PREMIUM, null),
                        new Empresa(null, "Fresh Market Deli", "pedidos@freshmarketdeli.com", "\"Calle Mayor, 5, 20003",
                                "123-456-7899", "CIF12345687", passwordEncoder.encode("1234"), "Tipo10",
                                "San Sebastián", Suscripcion.PREMIUM, null));
                empresaRepository.saveAll(empresas);
            }
            if (bancoDeAlimentosRepository.count() == 0) {
                List<BancoDeAlimentos> bancos = Arrays.asList(
                        new BancoDeAlimentos(null, "Banco de Alimentos de Alicante", "Calle Agost, 7", "965117190",
                                "alicante@bancodealimentos.es", "Alicante", passwordEncoder.encode("1234"), null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Madrid", "Carretera de Colmenar Km 13,600",
                                "917346383", "madrid@bancodealimentos.es", "Madrid", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Barcelona", "Carrer Motors, 122", "933464404",
                                "barcelona@bancodealimentos.es", "Barcelona", passwordEncoder.encode("1234"), null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Valencia", "Carrer dels Pedrapiquers, 5",
                                "963924460", "valencia@bancodealimentos.es", "Valencia", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Sevilla", "Carretera Sevilla-Málaga Km 5",
                                "954219311", "sevilla@bancodealimentos.es", "Sevilla", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Málaga", "Avenida Juan XXIII, 49",
                                "952131894", "malaga@bancodealimentos.es", "Málaga", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Bilbao", "Calle Ribera de Zorrozaurre, 48",
                                "944499158", "bilbao@bancodealimentos.es", "Bilbao", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Zaragoza", "Calle Mercazaragoza, 1",
                                "976737136", "zaragoza@bancodealimentos.es", "Zaragoza", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Murcia", "Calle Alcalde Clemente García, 14",
                                "968879940", "murcia@bancodealimentos.es", "Murcia", passwordEncoder.encode("1234"),
                                null),
                        new BancoDeAlimentos(null, "Banco de Alimentos de Granada", "Calle Loja, Nave 7", "958303578",
                                "granada@bancodealimentos.es", "Granada", passwordEncoder.encode("1234"), null));
                bancoDeAlimentosRepository.saveAll(bancos);
            }

            if (usuarioRepository.count() == 0) {
                List<Usuario> usuarios = Arrays.asList(
                        new Usuario(null, "admin1", passwordEncoder.encode("1234"), "admin1@example.com",
                                UsuarioRol.ADMINISTRADOR, null, null),
                        new Usuario(null, "admin2", passwordEncoder.encode("1234"), "admin2@example.com",
                                UsuarioRol.ADMINISTRADOR, null, null),
                        new Usuario(null, "admin3", passwordEncoder.encode("1234"), "admin3@example.com",
                                UsuarioRol.ADMINISTRADOR, null, null),
                        new Usuario(null, "admin4", passwordEncoder.encode("1234"), "admin4@example.com",
                                UsuarioRol.ADMINISTRADOR, null, null));
                usuarioRepository.saveAll(usuarios);
            }

            if (articulosRepository.count() == 0) {
                List<Articulo> articulos = Arrays.asList(
                        new Articulo(null, "La Nueva Ley de Desperdicio Alimentario en España:",
                                "Implicaciones y Oportunidades para las Empresas",
                                "/img/LeyDesperdicior.jpg",
                                "La entrada en vigor de la Ley 12/2023 de Prevención de las Pérdidas y el Desperdicio Alimentario marca un antes y después en la gestión de excedentes alimentarios en España. Esta normativa pionera establece nuevas obligaciones para las empresas del sector alimentario, transformando lo que antes era una opción en una responsabilidad legal.\r\n"
                                        + //
                                        "Obligaciones Principales\r\n" + //
                                        "Las empresas alimentarias deben implementar un plan de prevención que incluya:\r\n"
                                        + //
                                        "Inventario detallado de alimentos próximos a caducar\r\n" + //
                                        "Acuerdos con organizaciones sociales para la donación\r\n" + //
                                        "Transformación de productos no vendibles pero seguros\r\n" + //
                                        "Medidas para la reutilización en alimentación animal cuando sea posible\r\n" + //
                                        "Sanciones y Cumplimiento\r\n" + //
                                        "El incumplimiento puede resultar en multas de:\r\n" + //
                                        "Hasta 60,000€ por infracciones leves\r\n" + //
                                        "Hasta 150,000€ por infracciones graves\r\n" + //
                                        "Hasta 500,000€ por infracciones muy graves\r\n" + //
                                        "Oportunidades Empresariales\r\n" + //
                                        "La adaptación a la ley presenta ventajas significativas:\r\n" + //
                                        "Mejora de la imagen corporativa\r\n" + //
                                        "Deducciones fiscales por donaciones\r\n" + //
                                        "Reducción de costes operativos\r\n" + //
                                        "Fortalecimiento de relaciones comunitarias\r\n" + //
                                        ""),
                        new Articulo(null, "Desperdicio de alimentos\r\n",
                                "Reduciendo el Desperdicio de Alimentos: Datos y Soluciones\r\n",
                                "/img/DesperdicioAlimentarior.jpg",
                                "Cada año, un tercio de los alimentos producidos en el mundo se desperdician, lo que equivale a aproximadamente 1.300 millones de toneladas. Esto no solo representa una pérdida económica, sino también un impacto ambiental significativo. Según la Organización de las Naciones Unidas para la Alimentación y la Agricultura (FAO), el desperdicio de alimentos es responsable del 8-10% de las emisiones globales de gases de efecto invernadero.\r\n"
                                        + //
                                        "Causas del Desperdicio de Alimentos\r\n" + //
                                        "1. Sobreproducción y Distribución Ineficiente: Muchas veces, los alimentos no llegan a los consumidores debido a problemas en la cadena de suministro.\r\n"
                                        + //
                                        "2. Manejo Inadecuado en el Hogar: La mala planificación de compras y el almacenamiento incorrecto llevan a que los alimentos se echen a perder antes de ser consumidos.\r\n"
                                        + //
                                        "3. Normas Estéticas y Fechas de Vencimiento: Supermercados y consumidores rechazan productos que no cumplen con ciertos estándares visuales, aunque aún sean aptos para el consumo.\r\n"
                                        + //
                                        "Consecuencias del Desperdicio\r\n" + //
                                        "- Impacto Ambiental: Se desperdician recursos como agua y energía en la producción de alimentos que nunca se consumen.\r\n"
                                        + //
                                        "- Desigualdad Alimentaria: Mientras millones de toneladas de comida se desechan, más de 800 millones de personas en el mundo sufren hambre.\r\n"
                                        + //
                                        "- Pérdida Económica: El desperdicio de alimentos cuesta al mundo cerca de 1 billón de dólares al año.\r\n"
                                        + //
                                        "Soluciones para Reducir el Desperdicio\r\n" + //
                                        "1. Planificación Inteligente de Compras: Hacer listas y comprar sólo lo necesario evita que los alimentos se desperdicien en casa.\r\n"
                                        + //
                                        "2. Almacenamiento Correcto: Conocer cómo conservar frutas, verduras y otros alimentos puede prolongar su vida útil.\r\n"
                                        + //
                                        "3. Donaciones y Reaprovechamiento: Restaurantes, supermercados y hogares pueden donar comida en buen estado en lugar de desecharla.\r\n"
                                        + //
                                        "4. Consumo Consciente: Aprovechar al máximo los ingredientes, usar recetas de aprovechamiento y ser creativos con las sobras reduce el desperdicio.\r\n"
                                        + //
                                        "5. Uso de Tecnología: Aplicaciones y plataformas digitales ayudan a conectar negocios con consumidores interesados en comprar productos próximos a su fecha de vencimiento.\r\n"
                                        + //
                                        "Reducir el desperdicio de alimentos es una tarea de todos. Con pequeños cambios en nuestros hábitos, podemos contribuir a un sistema alimentario más sostenible y justo. ¿Qué acciones estás tomando para reducir tu desperdicio de comida?\r\n"),

                        new Articulo(null,
                                "Contamos con un equipo capacitado en APPCC, garantizando seguridad y calidad en cada proceso.",
                                "Garantizando Seguridad y Calidad en Cada Proceso con un Equipo Capacitado en APPCC\r\n"
                                        + //
                                        "",
                                "/img/seguridadAlimentaria.jpg",
                                "En el entorno actual, la seguridad alimentaria y la calidad en los procesos son dos de los pilares fundamentales para cualquier empresa que maneje productos de consumo. Para asegurar que cada etapa en la producción se realice bajo los más altos estándares de higiene y seguridad, contamos con un equipo altamente capacitado en el sistema de Análisis de Peligros y Puntos Críticos de Control (APPCC), lo que nos permite garantizar la máxima protección para nuestros consumidores.\r\n"
                                        + //
                                        "¿Qué es el APPCC?\r\n" + //
                                        "El APPCC es un sistema preventivo diseñado para identificar, evaluar y controlar los peligros que puedan comprometer la seguridad alimentaria en todas las etapas de la cadena de producción, desde la recepción de materias primas hasta la distribución del producto final. Este enfoque no solo se enfoca en la calidad del producto, sino que también contribuye a minimizar riesgos microbiológicos, químicos y físicos, asegurando que el producto que llega al consumidor final sea completamente seguro.\r\n"
                                        + //
                                        "Capacitación y Profesionalismo\r\n" + //
                                        "Nuestro equipo ha sido entrenado rigurosamente en los principios y aplicación del APPCC. Gracias a esta formación especializada, sabemos cómo implementar controles eficaces en cada punto crítico del proceso, como el almacenamiento, manipulación, procesamiento y distribución de los alimentos. Además, realizamos un seguimiento constante y auditorías internas para asegurarnos de que se mantenga el cumplimiento de los estándares establecidos.\r\n"
                                        + //
                                        "Beneficios del APPCC en Nuestro Proceso Productivo\r\n" + //
                                        "1. Prevención de Riesgos: El sistema APPCC permite identificar los riesgos antes de que ocurran, lo que facilita la implementación de medidas correctivas antes de que los problemas afecten la calidad o seguridad de los productos.\r\n"
                                        + //
                                        "2. Mejora Continua: La aplicación constante de las prácticas del APPCC fomenta una cultura de mejora continua, asegurando que los procesos se optimicen con el tiempo y se adapten a nuevas regulaciones o innovaciones en el sector.\r\n"
                                        + //
                                        "3. Confianza para el Consumidor: Al tener un equipo capacitado que cumple con los estándares más estrictos en seguridad alimentaria, nuestros clientes pueden confiar plenamente en que los productos que ofrecemos cumplen con los más altos niveles de calidad y seguridad.\r\n"
                                        + //
                                        "Conclusión\r\n" + //
                                        "La implementación y monitoreo de un sistema APPCC no solo es crucial para garantizar la seguridad alimentaria, sino también para mantener una reputación sólida en el mercado. Con un equipo capacitado, comprometido y experto en cada fase del proceso, aseguramos que cada producto que fabricamos cumpla con los más altos estándares de calidad y seguridad. Nuestra misión es proporcionar a nuestros clientes alimentos seguros y de calidad, siempre respaldados por la experiencia y dedicación de un equipo profesional que trabaja incansablemente por su bienestar.\r\n"
                                        + //
                                        ""),
                        new Articulo(null, "Conectando Empresas con Bancos de Alimentos para Combatir el Desperdicio",
                                "Save&Serve: Facilitamos la Solidaridad al Conectar Empresas con Bancos de Alimentos\r\n"
                                        + //
                                        "",
                                "/img/ConectandoEmpresas.jpg",
                                "En Save&Serve, nuestra misión es clara: ser el puente entre empresas con excedentes alimentarios y bancos de alimentos u organizaciones benéficas que luchan contra la inseguridad alimentaria. Ayudamos a prevenir el desperdicio de comida, garantizando que los productos sobrantes de hoteles, supermercados, restaurantes y otros proveedores lleguen a quienes más los necesitan, sin ningún costo para las ONGs o bancos de alimentos.\r\n"
                                        + //
                                        "Nuestra Propuesta de Valor\r\n" + //
                                        "Actuamos como intermediarios especializados en la recolección, transporte y gestión de alimentos sobrantes, garantizando que el proceso sea eficiente, seguro y libre de costos para las organizaciones que reciben las donaciones. De esta manera, las empresas contribuyen a la causa social y ambiental, mientras nosotros nos encargamos de toda la logística.\r\n"
                                        + //
                                        "¿Cómo Funciona Save&Serve?\r\n" + //
                                        "1. Recogida y Transporte Eficiente: Nos encargamos de la logística completa: desde la recogida de los alimentos sobrantes de empresas como supermercados, restaurantes y hoteles, hasta su transporte seguro y adecuado. Todo esto, por un costo para la empresa que cubre los gastos operativos, como el transporte y la gestión del proceso.\r\n"
                                        + //
                                        "2. Gestión Responsable y Segura: Nuestro equipo se asegura de que los alimentos sean recolectados, almacenados y distribuidos bajo los estándares más altos de seguridad alimentaria, manteniendo siempre la calidad de los productos. La gestión es transparente, eficiente y completamente orientada a minimizar el desperdicio.\r\n"
                                        + //
                                        "3. Distribución Gratuita a Bancos de Alimentos y ONGs: Lo que hace único a nuestro modelo es que los alimentos que recogemos y gestionamos se entregan a los bancos de alimentos y organizaciones benéficas sin ningún costo para ellos. De esta manera, garantizamos que las donaciones lleguen a las personas más necesitadas sin que las ONGs tengan que invertir recursos en la logística.\r\n"
                                        + //
                                        "Beneficios de Save&Serve para Todos los Involucrados\r\n" + //
                                        "- Para las Empresas: Ayudan a reducir el desperdicio de alimentos de manera responsable y contribuyen al bienestar social y ambiental. Además, cuentan con una solución logística eficaz, gestionada por expertos, lo que facilita su compromiso con la sostenibilidad.\r\n"
                                        + //
                                        "- Para los Bancos de Alimentos y ONGs: Reciben alimentos de calidad sin necesidad de cubrir costos logísticos, lo que les permite optimizar sus recursos y concentrarse en la distribución entre las personas que más lo necesitan.\r\n"
                                        + //
                                        "- Para la Sociedad: Al reducir el desperdicio de alimentos y redistribuirlos a quienes enfrentan carencias, promovemos una cultura de solidaridad y sostenibilidad que beneficia a toda la comunidad.\r\n"
                                        + //
                                        "Un Futuro Sin Desperdicio\r\n" + //
                                        "La labor de Save&Serve tiene un impacto positivo en la sociedad, el medio ambiente y la economía. No solo ayudamos a evitar que miles de toneladas de comida sean desechadas, sino que también contribuimos a la construcción de un sistema más justo y solidario. Cada empresa que decide colaborar con nosotros está tomando una decisión importante en favor de un mundo sin hambre y con menos desperdicio.\r\n"
                                        + //
                                        "En Save&Serve, estamos orgullosos de ser ese enlace entre la generosidad de las empresas y las necesidades de los más vulnerables, asegurando que cada alimento rescatado sea un paso más hacia un futuro más justo y sostenible para todos.\r\n"
                                        + //
                                        "")

                );
                articulosRepository.saveAll(articulos);

            }

            // ejemplo de donacion
            // if (donacionRepository.count() == 0) {
            // // Buscar una empresa existente
            // Optional<Empresa> empresaOpt = empresaRepository.findById(1L);
            // Optional<BancoDeAlimentos> bancoOpt =
            // bancoDeAlimentosRepository.findById(1L);
            // Optional<Transporte> transporteOpt = transporteRepository.findById(1L);

            // if (empresaOpt.isPresent() && bancoOpt.isPresent() &&
            // transporteOpt.isPresent()) {
            // Empresa empresa = empresaOpt.get();
            // BancoDeAlimentos banco = bancoOpt.get();
            // Transporte transporte = transporteOpt.get();

            // // Crear dos donaciones de ejemplo
            // Donacion donacion1 = new Donacion(
            // null, // idDonacion (autogenerado)
            // new BigDecimal("500.00"), // totalDonacion
            // LocalDate.now(), // fechaEntrega
            // EstadoEnvio.PENDIENTE, // estadoEnvio
            // null, // donacionesRelacionadas
            // null, // donacionPrincipal
            // empresa, // empresa
            // null, // lineasProducto (se pueden agregar después)
            // banco, // bancoDeAlimentos
            // transporte // transporte
            // );

            // Donacion donacion2 = new Donacion(
            // null,
            // new BigDecimal("750.00"),
            // LocalDate.now().plusDays(3),
            // EstadoEnvio.ENVIADO,
            // null,
            // null,
            // empresa,
            // null,
            // banco,
            // transporte
            // );

            // // Guardar las donaciones en la base de datos
            // donacionRepository.save(donacion1);
            // donacionRepository.save(donacion2);

            // System.out.println("✅ Donaciones de ejemplo insertadas en la base de
            // datos.");
            // } else {
            // System.out.println("⚠️ No se encontraron empresa, banco o transporte para
            // asociar a las donaciones.");
            // }
            // }

            if (alergenosRepository.count() == 0) {
                List<Alergenos> alergenos = Arrays.asList(
                        new Alergenos(null, "Gluten", "gluten.png", null),
                        new Alergenos(null, "Crustáceos", "crustaceos.png", null),
                        new Alergenos(null, "Huevos", "huevos.png", null),
                        new Alergenos(null, "Pescado", "pescado.png", null),
                        new Alergenos(null, "Cacahuetes", "cacahuetes.png", null),
                        new Alergenos(null, "Soja", "soja.png", null),
                        new Alergenos(null, "Lácteos", "lacteos.png", null),
                        new Alergenos(null, "Frutos de cáscara", "frutos_cascara.png", null),
                        new Alergenos(null, "Apio", "apio.png", null),
                        new Alergenos(null, "Mostaza", "mostaza.png", null),
                        new Alergenos(null, "Granos de sésamo", "sesamo.png", null),
                        new Alergenos(null, "Dióxido de azufre y sulfitos", "sulfitos.png", null),
                        new Alergenos(null, "Altramuces", "altramuces.png", null),
                        new Alergenos(null, "Moluscos", "moluscos.png", null));
                alergenosRepository.saveAll(alergenos);
            }

            if (transporteRepository.count() == 0) {
                List<Transporte> transportes = Arrays.asList(
                    new Transporte(null, "Transportes Norte", new HashSet<>(Arrays.asList(TipoTransporte.SECO, TipoTransporte.REFRIGERADO))),
                    new Transporte(null, "Logística Express", new HashSet<>(Arrays.asList(TipoTransporte.REFRIGERADO, TipoTransporte.CONGELADO))),
                    new Transporte(null, "Fletes del Sur", new HashSet<>(Arrays.asList(TipoTransporte.SECO, TipoTransporte.CONGELADO))),
                    new Transporte(null, "TodoTerreno Express", new HashSet<>(Arrays.asList(TipoTransporte.SECO, TipoTransporte.CONGELADO, TipoTransporte.REFRIGERADO)))
                   
                );
                transporteRepository.saveAll(transportes);
            }
        };
    }
}
