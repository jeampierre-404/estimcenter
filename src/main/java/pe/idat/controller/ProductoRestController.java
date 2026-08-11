package pe.idat.controller;

import java.util.Collection;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

// 🔥 IMPORTACIONES DE CLOUDINARY 🔥
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import pe.idat.model.Producto;
import pe.idat.service.ProductoService;
import pe.idat.model.MovimientoInventario;
import pe.idat.model.MovimientoInventario.TipoMovimiento;
import pe.idat.service.MovimientoInventarioService;
import pe.idat.model.Usuario;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoInventarioService movimientoService;

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        Collection<Producto> items = productoService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/buscar/{idProducto}")
    public ResponseEntity<?> buscar(@PathVariable Integer idProducto) {
        Producto producto = productoService.findById(idProducto);
        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestPart("producto") String productoJson,
                                     @RequestPart(value = "filePrincipal", required = false) MultipartFile filePrincipal,
                                     @RequestPart(value = "fileSala", required = false) MultipartFile fileSala) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            producto.setIdProducto(null);

            if (filePrincipal != null && !filePrincipal.isEmpty()) {
                String urlImagen = guardarArchivoEnCloudinary(filePrincipal);
                producto.setImagen(urlImagen); // Guardamos directamente la URL de Cloudinary
            }

            if (fileSala != null && !fileSala.isEmpty()) {
                String urlImagenSala = guardarArchivoEnCloudinary(fileSala);
                producto.setImagenSala(urlImagenSala);
            }

            productoService.insert(producto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{idProducto}")
    public ResponseEntity<?> editar(@PathVariable Integer idProducto, 
                                    @RequestPart("producto") String productoJson,
                                    @RequestPart(value = "filePrincipal", required = false) MultipartFile filePrincipal,
                                    @RequestPart(value = "fileSala", required = false) MultipartFile fileSala) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Producto newProducto = objectMapper.readValue(productoJson, Producto.class);
            Producto producto = productoService.findById(idProducto);

            if (producto != null) {
                producto.setCodigo(newProducto.getCodigo());
                producto.setNombre(newProducto.getNombre());
                producto.setDescripcion(newProducto.getDescripcion());
                producto.setPrecio(newProducto.getPrecio());
                producto.setUnidadMedida(newProducto.getUnidadMedida());
                producto.setRendimientoCaja(newProducto.getRendimientoCaja());
                producto.setStockMinimo(newProducto.getStockMinimo());
                producto.setCategoria(newProducto.getCategoria());
                producto.setStockActual(newProducto.getStockActual());
                producto.setEstado(newProducto.getEstado());

                if (filePrincipal != null && !filePrincipal.isEmpty()) {
                    String urlImagen = guardarArchivoEnCloudinary(filePrincipal);
                    producto.setImagen(urlImagen);
                }

                if (fileSala != null && !fileSala.isEmpty()) {
                    String urlImagenSala = guardarArchivoEnCloudinary(fileSala);
                    producto.setImagenSala(urlImagenSala);
                }

                productoService.update(producto);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable Integer id) {
        pe.idat.model.Producto prod = productoService.findById(id);
        if (prod != null) {
            try {
                productoService.delete(id);
                return ResponseEntity.ok().body("{\"mensaje\": \"eliminado_fisico\"}");
            } catch (Exception e) {
                prod.setEstado("INACTIVO");
                productoService.update(prod);
                return ResponseEntity.ok().body("{\"mensaje\": \"baja_logica\"}");
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/agregar-stock/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> agregarStock(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        pe.idat.model.Producto prod = productoService.findById(id);
        if (prod != null) {
            String cantidadStr = String.valueOf(request.get("cantidad"));
            BigDecimal cantidadAdicional = new BigDecimal(cantidadStr);

            prod.setStockActual(prod.getStockActual().add(cantidadAdicional));
            productoService.update(prod);

            MovimientoInventario mov = new MovimientoInventario();
            mov.setTipo(TipoMovimiento.ENTRADA);
            mov.setCantidad(cantidadAdicional);
            mov.setFecha(new Date());
            mov.setMotivo("Ingreso manual de mercadería");
            mov.setProducto(prod);

            Usuario user = new Usuario();
            user.setIdUsuario(1);
            mov.setUsuario(user);

            movimientoService.insert(mov);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 🔥 LA MAGIA DE CLOUDINARY 🔥
    private String guardarArchivoEnCloudinary(MultipartFile file) throws Exception {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "e4ah2gm7", 
            "api_key", "946497571792348", 
            "api_secret", "QgiAoj1_umh3wL5LnQRv4W9QHBA", 
            "secure", true
        ));

        // Le decimos que lo guarde en una carpeta y lo convierta a webp ligero
        Map params = ObjectUtils.asMap(
            "folder", "estim_center_productos",
            "format", "webp",
            "quality", "auto"
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        
        // Devolvemos el link seguro (https)
        return uploadResult.get("secure_url").toString();
    }
}