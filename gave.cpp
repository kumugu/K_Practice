#include <iostream>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;

class GameServer {
public:
    GameServer(boost::asio::io_context& io_context, short port)
        : acceptor_(io_context, tcp::endpoint(tcp::v4(), port)) {
        start_accept();
    }

private:
    void start_accept() {
        socket_ = std::make_shared<tcp::socket>(acceptor_.get_io_context());
        acceptor_.async_accept(*socket_, [this](const boost::system::error_code& error) {
            if (!error) {
                std::cout << "Client connected" << std::endl;
                start_read();
            }
            start_accept();
        });
    }

    void start_read() {
        auto self(shared_from_this());
        socket_->async_read_some(boost::asio::buffer(data_),
            [this, self](const boost::system::error_code& error, std::size_t length) {
                if (!error) {
                    std::cout << "Received: " << std::string(data_.data(), length) << std::endl;
                    start_write(length);
                }
            });
    }

    void start_write(std::size_t length) {
        auto self(shared_from_this());
        boost::asio::async_write(*socket_, boost::asio::buffer(data_, length),
            [this, self](const boost::system::error_code& error, std::size_t /*length*/) {
                if (!error) {
                    start_read();
                }
            });
    }

    tcp::acceptor acceptor_;
    std::shared_ptr<tcp::socket> socket_;
    std::array<char, 128> data_;
};

int main() {
    try {
        boost::asio::io_context io_context;
        GameServer server(io_context, 12345);
        io_context.run();
    } catch (std::exception& e) {
        std::cerr << "Exception: " << e.what() << std::endl;
    }

    return 0;
}
