import { useState, useEffect } from 'react'; // Foi adicionado os hooks
import { View, Text, Button, FlatList, StyleSheet, PermissionsAndroid, Platform } from 'react-native';
import { BleManager } from 'react-native-ble-plx';

const manager = new BleManager();

// Corrigido: Nome do componente com letra maiúscula
export default function BleScannerComponent() {

  // Corrigido: Inicialização do estado devices com tipo correto
  const [devices, setDevices] = useState([{ id: '', name: '' }]); 
  // Para JS puro, basta garantir que sempre adiciona objetos {id, name}

  // O estado 'radioPowerOn' verifica se o bluetooth está ligado (true) ou desligado (false)
  const [radioPowerOn, setRadioPowerOn] = useState(false);

  useEffect(() => {
    const subscription = manager.onStateChange((state) => {
      if (state === "PoweredOn") {
        setRadioPowerOn(true);
        subscription.remove();
      }
    }, true);
    return () => {
      subscription.remove();
      manager.destroy();
    }
  }, [])

  const requestBluetoothPermission = async () => {

    if (Platform.OS === 'android') {
      // Busca saber qual API -  A partir do android 12 (API31), as permissões explicitas do
      // bluetooth são necessárias
      const apilevel = parseInt(Platform.Version.toString(), 10);
      if (apilevel < 31) {
        // Para android < 12, a permissão de localização é suficiente.
        const granted = await PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
          {
            title: 'Permissão de localização',
            message: 'O app precisa de acesso á sua localização para scannear dispositivos bluetooth',
            buttonPositive: 'OK'
          },
        );
        return granted === PermissionsAndroid.RESULTS.GRANTED;
      } else {
        // Caso Android é versão 12 ou mais, deve ser solicitada as novas permissões de Bluetooth
        const result = await PermissionsAndroid.requestMultiple(
          [
            PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN,
            PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION // Permissão localização ainda necessária
          ]
        );

        return (
          result[PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN] === PermissionsAndroid.RESULTS.GRANTED &&
          result[PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT] === PermissionsAndroid.RESULTS.GRANTED &&
          result[PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION] === PermissionsAndroid.RESULTS.GRANTED
        );
      }
    }
    return true;
  }

  const scanForDevices = async () => {
    const hasPermission = await requestBluetoothPermission();
    if(!hasPermission){
      alert('Permissão negada. O aplicativo não pode escanear dispositivos bluetooth!');
      return;
    }
    if(!radioPowerOn){
      alert('Por favor, ligue o Bluetooth para escanear dispositivos.')
      return;
    }
    // No início do scanForDevices, limpe o array corretamente:
    setDevices([]);
    manager.startDeviceScan(null, null, (error, device) => {
      if(error){
        console.log('ERROR SCAN: ', error)
        if(error.errorCode === 601){
          alert('Erro permissão de SCAN. Verifique a permissão de dispositivos proximo')
        }
        manager.stopDeviceScan();
        return;
      }
      if (device && device.name){
        setDevices(prevDevices => {
          if(!prevDevices.some(d => d.id === device.id)){
            return [...prevDevices, { id: device.id, name: device.name || 'Sem nome' }];
          }
          return prevDevices;
        })
      }
    });

    // Corrigido: Sintaxe do setTimeout
    setTimeout(()=>{
      manager.stopDeviceScan();
    }, 5000);
  }

  return(
    <View style={styles.container}>
      <Text style={styles.title}>
        Dispositivos Bluetooth encontrados:
      </Text>
      <Button title='Scan devices'  onPress={scanForDevices}/>
      <FlatList 
        // No FlatList, filtre para não mostrar o item vazio inicial:
        data={devices.filter(d => d.id)}
        keyExtractor={item => item.id}
        renderItem={({item})=> (
          <Text style={styles.deviceText}>
            {item.name}  --- ({item.id})
          </Text>
        )}
      />
    </View>
  );

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f6fa',
    padding: 20,
    paddingTop: 50,
  },
  title: {
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 20,
    color: '#273c75',
    textAlign: 'center',
  },
  deviceText: {
    fontSize: 16,
    padding: 12,
    marginVertical: 6,
    backgroundColor: '#dff9fb',
    borderRadius: 8,
    color: '#353b48',
    elevation: 2,
  },
});


