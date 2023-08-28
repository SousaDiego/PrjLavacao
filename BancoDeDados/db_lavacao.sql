-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 09-Jun-2023 às 15:20
-- Versão do servidor: 8.0.32
-- versão do PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `db_lavacao_1`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `celular` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `dataCadastro` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`id`, `nome`, `celular`, `email`, `endereco`, `dataCadastro`) VALUES
(1, 'Bruno Luz2', '(48) 99921-2409', 'bruno.fel24@aluno.ifsc.edu.br', 'Rua Mauro Ramos,1024', '2023-05-17'),
(2, 'SOFTPLAN PLANEJAMENTO E SISTEMAS S/A', '(48) 3027-8000', 'administrativo@softplan.com.br', 'Av Luiz Boiteux Piazza', '2023-05-18'),
(5, 'teste', '489999999999', 'teste@teste.com', NULL, '2023-05-05');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cor`
--

DROP TABLE IF EXISTS `cor`;
CREATE TABLE IF NOT EXISTS `cor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `cor`
--

INSERT INTO `cor` (`id`, `nome`) VALUES
(5, 'Amarelo'),
(6, 'Azul'),
(7, 'Preta'),
(8, 'Vermelho'),
(9, 'Branco');

-- --------------------------------------------------------

--
-- Estrutura da tabela `item_os`
--

DROP TABLE IF EXISTS `item_os`;
CREATE TABLE IF NOT EXISTS `item_os` (
  `id` int NOT NULL AUTO_INCREMENT,
  `valorServico` decimal(10,2) DEFAULT NULL,
  `observacoes` varchar(100) DEFAULT NULL,
  `id_servico` int NOT NULL,
  `id_ordem_servico` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_OS_servico` (`id_servico`),
  KEY `fk_item_OS_ordem_servico` (`id_ordem_servico`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `item_os`
--

INSERT INTO `item_os` (`id`, `valorServico`, `observacoes`, `id_servico`, `id_ordem_servico`) VALUES
(1, '150.00', '', 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `marca`
--

DROP TABLE IF EXISTS `marca`;
CREATE TABLE IF NOT EXISTS `marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `marca`
--

INSERT INTO `marca` (`id`, `nome`) VALUES
(1, 'Jeep'),
(2, 'Chevrolet'),
(4, 'Ford'),
(6, 'Fiat'),
(7, 'Volkswagem');

-- --------------------------------------------------------

--
-- Estrutura da tabela `modelo`
--

DROP TABLE IF EXISTS `modelo`;
CREATE TABLE IF NOT EXISTS `modelo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `id_marca` int NOT NULL,
  `ECategoria` enum('PADRAO','PEQUENO','MEDIO','GRANDE','MOTO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PADRAO',
  PRIMARY KEY (`id`,`id_marca`),
  KEY `id_marca` (`id_marca`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `modelo`
--

INSERT INTO `modelo` (`id`, `descricao`, `id_marca`, `ECategoria`) VALUES
(4, '4x4', 4, 'PADRAO'),
(5, 'Sedan', 1, 'PADRAO'),
(7, 'SUV', 2, 'PADRAO'),
(8, 'Hatch', 6, 'PADRAO');

-- --------------------------------------------------------

--
-- Estrutura da tabela `motor`
--

DROP TABLE IF EXISTS `motor`;
CREATE TABLE IF NOT EXISTS `motor` (
  `id_modelo` int NOT NULL,
  `potencia` int NOT NULL DEFAULT '100',
  `tipoCombustivel` enum('GASOLINA','ETANOL','FLEX','GNV','OUTRO') NOT NULL DEFAULT 'GASOLINA',
  PRIMARY KEY (`id_modelo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `motor`
--

INSERT INTO `motor` (`id_modelo`, `potencia`, `tipoCombustivel`) VALUES
(4, 800, 'GASOLINA'),
(5, 200, 'GASOLINA'),
(7, 500, 'GASOLINA'),
(8, 202, 'GASOLINA');

-- --------------------------------------------------------

--
-- Estrutura da tabela `ordem_servico`
--

DROP TABLE IF EXISTS `ordem_servico`;
CREATE TABLE IF NOT EXISTS `ordem_servico` (
  `numero` int NOT NULL AUTO_INCREMENT,
  `total` decimal(10,2) NOT NULL,
  `agenda` date NOT NULL,
  `desconto` double DEFAULT NULL,
  `id_veiculo` int NOT NULL,
  `status` enum('ABERTA','FECHADA','CANCELADA') NOT NULL DEFAULT 'ABERTA',
  PRIMARY KEY (`numero`),
  KEY `fk_ordemServico_veiculo` (`id_veiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `ordem_servico`
--

INSERT INTO `ordem_servico` (`numero`, `total`, `agenda`, `desconto`, `id_veiculo`, `status`) VALUES
(1, '150.00', '2023-06-09', 0, 5, 'ABERTA');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa_fisica`
--

DROP TABLE IF EXISTS `pessoa_fisica`;
CREATE TABLE IF NOT EXISTS `pessoa_fisica` (
  `id_cliente` int NOT NULL,
  `cpf` varchar(100) NOT NULL,
  `dataNascimento` date DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `pessoa_fisica`
--

INSERT INTO `pessoa_fisica` (`id_cliente`, `cpf`, `dataNascimento`) VALUES
(1, '07286958984', '1989-09-24'),
(5, '555555555555599999', '2023-05-05');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa_juridica`
--

DROP TABLE IF EXISTS `pessoa_juridica`;
CREATE TABLE IF NOT EXISTS `pessoa_juridica` (
  `id_cliente` int NOT NULL,
  `cnpj` varchar(100) NOT NULL,
  `inscricaoEstadual` varchar(100) DEFAULT NULL,
  `dataNascimento` date DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `pessoa_juridica`
--

INSERT INTO `pessoa_juridica` (`id_cliente`, `cnpj`, `inscricaoEstadual`, `dataNascimento`) VALUES
(2, '82.845.322/0001-04', '12345', '1990-11-28');

-- --------------------------------------------------------

--
-- Estrutura da tabela `servico`
--

DROP TABLE IF EXISTS `servico`;
CREATE TABLE IF NOT EXISTS `servico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `pontos` int NOT NULL,
  `categoria` enum('PADRAO','PEQUENO','MEDIO','GRANDE','MOTO') NOT NULL DEFAULT 'PADRAO',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `servico`
--

INSERT INTO `servico` (`id`, `descricao`, `valor`, `pontos`, `categoria`) VALUES
(1, 'Polimento', 150, 10, 'PADRAO');

-- --------------------------------------------------------

--
-- Estrutura da tabela `veiculo`
--

DROP TABLE IF EXISTS `veiculo`;
CREATE TABLE IF NOT EXISTS `veiculo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `placa` varchar(100) NOT NULL,
  `observacoes` varchar(100) NOT NULL,
  `id_modelo` int NOT NULL,
  `id_cor` int NOT NULL,
  `id_cliente` int NOT NULL,
  PRIMARY KEY (`id`,`id_modelo`,`id_cor`,`id_cliente`),
  KEY `id_cor` (`id_cor`),
  KEY `id_modelo` (`id_modelo`),
  KEY `id_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `veiculo`
--

INSERT INTO `veiculo` (`id`, `placa`, `observacoes`, `id_modelo`, `id_cor`, `id_cliente`) VALUES
(3, 'QIE1856', 'carro com retrovisor dianteiro direito trincado', 4, 7, 1),
(4, 'RAT3598', 'alugado', 7, 6, 2),
(5, 'TESTE', 'teste', 5, 7, 1);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `item_os`
--
ALTER TABLE `item_os`
  ADD CONSTRAINT `fk_item_OS_ordem_servico` FOREIGN KEY (`id_ordem_servico`) REFERENCES `ordem_servico` (`numero`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_item_OS_servico` FOREIGN KEY (`id_servico`) REFERENCES `servico` (`id`);

--
-- Limitadores para a tabela `modelo`
--
ALTER TABLE `modelo`
  ADD CONSTRAINT `modelo_ibfk_1` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`);

--
-- Limitadores para a tabela `motor`
--
ALTER TABLE `motor`
  ADD CONSTRAINT `fk_motor_modelo` FOREIGN KEY (`id_modelo`) REFERENCES `modelo` (`id`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `ordem_servico`
--
ALTER TABLE `ordem_servico`
  ADD CONSTRAINT `fk_ordemServico_veiculo` FOREIGN KEY (`id_veiculo`) REFERENCES `veiculo` (`id`);

--
-- Limitadores para a tabela `pessoa_fisica`
--
ALTER TABLE `pessoa_fisica`
  ADD CONSTRAINT `fk_pessoaFisica_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `pessoa_juridica`
--
ALTER TABLE `pessoa_juridica`
  ADD CONSTRAINT `fk_pessoaJuridica_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `veiculo`
--
ALTER TABLE `veiculo`
  ADD CONSTRAINT `veiculo_ibfk_1` FOREIGN KEY (`id_cor`) REFERENCES `cor` (`id`),
  ADD CONSTRAINT `veiculo_ibfk_2` FOREIGN KEY (`id_modelo`) REFERENCES `modelo` (`id`),
  ADD CONSTRAINT `veiculo_ibfk_3` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
