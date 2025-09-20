import { useState } from "react";
import { View, TextInput, Button, StyleSheet } from "react-native";

export default function userForm({onUserAdded}) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");   

  //adicionar usuario
    const addUser = async () => {
        if (!name && !email) return;
        try {
            const response = await fetch("", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name, email }),
            });
            setName("");    //limpar os campos
            setEmail(""); //limpar os campos
            onUserAdded(); //recarregar a lista de usuarios
        } catch (error) {
            console.error("Error de POST", error.message);
        } 
    };
    return (
        <View style={styles.form}>
            <TextInput
                style={styles.input}
                placeholder="Nome"
                value={name}
                onChangeText={setName}
            />
            <TextInput
                style={styles.input}
                placeholder="Email"
                value={email}
                onChangeText={setEmail}
            />
            <Button title="Adicionar Usuario" onPress={addUser} />
        </View>
    );
}

const styles = StyleSheet.create({
    form: { marginBottom: 20 },
    input: {
        borderWidth:1,
        borderColor:"#ccc",
        padding:8,
        marginBottom:10,
        borderRadius:5,
        }   
    });
    
 