# LobbyAddon


 LobbyAddon es un plugin spigot que mediante el sistema de [Lobby System](https://github.com/ObedMz/LobbySystem) permite
ejecutar un comando personalizable desde un servidor spigot o integrando la API para poder comunicarse con el BungeeCord y envíar
a los jugadores a las lobbys.

## Usos
 Este Addon fue creado con el proposito de que los jugadores puedan ir a las lobbys de la network de distinta manera,
  ya sea haciendo click a un texto, haciendo click a una cama o lo que se te ocurra!
 
 ## Cómo usar
 
### Comando
Este plugin te da la facilidad de tener un comado spigot personalizado, el cual puede ser ejecutado con cualquier plugin externo,
para configurarlo, debes editar la config.yml del mismo plugin

```yml
config:
  lobby:
    ##Habilitar el uso del comando
    enable: true
    ##Player command
    command: "vlobby"
    permission: "vlobby.use"
    message:
      sucess: ""
      denied: "&cNo tienes permisos para usar esto."

```
Una vez hecho puedes iniciar tu servidor de spigot y el comando se registrará automaticamente y al ser ejecutado por algún jugador
se podrá transportar a alguna lobby que tengas en la network.


### API

Si eres un programador y deseas utilizar este plugin dentro de tu codigo para poder envíar a los jugadores a otras lobbys mediante eventos de spigot,
puedes implementar este Addon como librería.

### Ejemplo
```java
@EventHandler
public void BedInteract(PlayerInteractEvent) {
   if(e.getItemInHand().getType() == Material.BED) {
    LobbyAddon.getInstance().LobbyTP(e.getPlayer());
   }

}

```

### Nota:
Recuerda agregar el Addon como dependencia.
```yml
depend: [LobbyAddon]

```





