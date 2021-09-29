import {Row, Form, Input, Button} from "antd";
import {useHistory} from "react-router-dom";
const axios = require('axios');

export default function Login() {
    const history = useHistory();

    const onSubmit = async (values) => {
        let jwt;
        try {
            jwt = await axios.post('http://localhost:8080/authenticate', values);
        } catch(err) {
            alert("wrong username or password");
            return
        }
        localStorage.setItem('accessToken', jwt.data.accessToken);

        if (values.username === 'admin') {
            history.push('/admin')
        } else {
            history.push('/user')
        }
    }

    return <>
        <Row>
            <h1>login</h1>
        </Row>
        <Form
            name="basic"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            initialValues={{ remember: true }}
            onFinish={onSubmit}
            autoComplete="off">
            <Row>
                <Form.Item
                    label='username'
                    name='username'
                    rules={[{ required: true, message: 'Please input your username!'}]}
                >
                    <Input/>
                </Form.Item>
            </Row>
            <Row>
                <Form.Item
                    label='password'
                    name='password'
                    rules={[{ required: true, message: 'Please input password!'}]}
                >
                    <Input type='password'/>
                </Form.Item>
            </Row>
            <Row>
                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type='primary' htmlType='submit'>
                        Submit
                    </Button>
                </Form.Item>
            </Row>
        </Form>
    </>
}