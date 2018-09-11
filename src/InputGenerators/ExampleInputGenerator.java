package InputGenerators;

import Geometry.Native.Vertex;

import java.util.HashMap;

public class ExampleInputGenerator implements InputGenerator {

    private static final Vertex[] uniform = {new Vertex(0, 0, 0,1),
            new Vertex(0, 100, 1,1),
            new Vertex(0, 200, 2,1),
            new Vertex(0, 300, 3,1),
            new Vertex(100, 0, 4,1),
            new Vertex(100, 100, 5,1),
            new Vertex(100, 200, 6,1),
            new Vertex(100, 300, 7,1),
            new Vertex(200, 0, 8,1),
            new Vertex(200, 100, 9,1),
            new Vertex(200, 200, 10,1),
            new Vertex(200, 300, 11,1),
            new Vertex(300, 0, 12,1),
            new Vertex(300, 100, 13,1),
            new Vertex(300, 200, 14,1),
            new Vertex(300, 300, 15,1),
    };

    private static final Vertex[] uniform2 = {new Vertex(50, 50, 0,1),
            new Vertex(50, 150, 1,1),
            new Vertex(50, 250, 2,1),
            new Vertex(50, 350, 3,1),
            new Vertex(150, 50, 4,1),
            new Vertex(150, 150, 5,1),
            new Vertex(150, 250, 6,1),
            new Vertex(150, 350, 7,1),
            new Vertex(250, 50, 8,1),
            new Vertex(250, 150, 9,1),
            new Vertex(250, 250, 10,1),
            new Vertex(250, 350, 11,1),
            new Vertex(350, 50, 12,1),
            new Vertex(350, 150, 13,1),
            new Vertex(350, 250, 14,1),
            new Vertex(350, 350, 15,1),
    };

    private static final  Vertex[] badcase = {
            new Vertex(137,298,46),
            new Vertex(82,47,25),
            new Vertex(262,124,40),
            new Vertex(65,134,23),
            new Vertex(244,40,34),
            new Vertex(109,144,47),
            new Vertex(22,202,22),
            new Vertex(187,121,0),
            new Vertex(52,196,2),
            new Vertex(58,111,15),
            new Vertex(26,59,36),
            new Vertex(205,94,37),
    };

    private static final Vertex[] badcase2 = {
            new Vertex(127, 122, 0),
            new Vertex(146, 59, 1),
            new Vertex(261, 76, 2),
            new Vertex(205, 58, 3),
            new Vertex(225, 8, 4),
            new Vertex(219, 237, 5),
            new Vertex(6, 291, 6),
            new Vertex(160, 184, 7),
            new Vertex(105, 57, 8),
            new Vertex(76, 12, 9),
            new Vertex(10, 166, 10),
            new Vertex(105, 66, 11),
            new Vertex(268, 191, 12),
            new Vertex(80, 194, 13),
            new Vertex(136, 90, 14),
            new Vertex(132, 234, 15),
            new Vertex(256, 203, 16),
            new Vertex(35, 250, 17),
            new Vertex(177, 219, 18),
            new Vertex(131, 59, 19),
            new Vertex(212, 11, 20),
            new Vertex(272, 21, 21),
            new Vertex(106, 21, 22),
            new Vertex(120, 76, 23),
            new Vertex(21, 57, 24),
            new Vertex(32, 22, 25),
            new Vertex(155, 79, 26),
            new Vertex(255, 118, 27),
            new Vertex(112, 187, 28),
            new Vertex(70, 200, 29),
            new Vertex(208, 95, 30),
            new Vertex(104, 213, 31),
            new Vertex(82, 97, 32),
            new Vertex(110, 96, 33),
            new Vertex(169, 219, 34),
            new Vertex(126, 239, 35),
            new Vertex(78, 28, 36),
            new Vertex(180, 252, 37),
            new Vertex(103, 117, 38),
            new Vertex(248, 183, 39),
            new Vertex(199, 198, 40),
            new Vertex(5, 145, 41),
            new Vertex(42, 192, 42),
            new Vertex(162, 127, 43),
            new Vertex(72, 98, 44),
            new Vertex(58, 273, 45),
            new Vertex(135, 264, 46),
            new Vertex(12, 206, 47),
            new Vertex(190, 136, 48),
            new Vertex(66, 129, 49),
    };

    private static final Vertex[] badcase3 = {
            new Vertex(192, 160, 0),
            new Vertex(50, 185, 1),
            new Vertex(288, 215, 2),
            new Vertex(276, 175, 3),
            new Vertex(81, 68, 4),
            new Vertex(246, 10, 5),
            new Vertex(266, 217, 6),
            new Vertex(263, 185, 7),
            new Vertex(164, 211, 8),
            new Vertex(277, 193, 9),
            new Vertex(73, 239, 10),
            new Vertex(5, 254, 11),
            new Vertex(233, 194, 12),
            new Vertex(216, 285, 13),
            new Vertex(243, 96, 14),
            new Vertex(66, 94, 15),
            new Vertex(150, 169, 16),
            new Vertex(238, 240, 17),
            new Vertex(199, 7, 18),
            new Vertex(296, 206, 19),
            new Vertex(280, 216, 20),
            new Vertex(16, 49, 21),
            new Vertex(192, 141, 22),
            new Vertex(132, 132, 23),
            new Vertex(222, 227, 24),
            new Vertex(25, 59, 25),
            new Vertex(95, 186, 26),
            new Vertex(287, 13, 27),
            new Vertex(227, 140, 28),
            new Vertex(289, 173, 29),
            new Vertex(112, 101, 30),
            new Vertex(261, 201, 31),
            new Vertex(23, 14, 32),
            new Vertex(290, 102, 33),
            new Vertex(162, 262, 34),
            new Vertex(132, 99, 35),
            new Vertex(259, 273, 36),
            new Vertex(221, 236, 37),
            new Vertex(106, 60, 38),
            new Vertex(85, 242, 39),
            new Vertex(4, 182, 40),
            new Vertex(13, 206, 41),
            new Vertex(68, 155, 42),
            new Vertex(166, 296, 43),
            new Vertex(284, 290, 44),
            new Vertex(260, 228, 45),
            new Vertex(197, 176, 46),
            new Vertex(154, 160, 47),
            new Vertex(256, 264, 48),
            new Vertex(280, 19, 49),
    };

    private static final Vertex[] fixed = {new Vertex(78, 164, 0 ,1),
            new Vertex(204, 151, 1, 1),
            new Vertex(294, 104, 2, 1),
            new Vertex(226, 103, 3, 1),
            new Vertex(223, 221, 4,1 ),
            new Vertex(110, 217, 5, 1),
            new Vertex(52, 29, 6,1),
            new Vertex(228, 27, 7,1),
            new Vertex(59, 28, 8, 1),
            new Vertex(28, 188, 9, 1),
            new Vertex(250, 40, 10, 1),
            new Vertex(38, 52, 11, 1),
            new Vertex(228, 81, 12,2),
            new Vertex(17, 273, 13,2),
            new Vertex(32, 157, 14,2),
            new Vertex(45, 138, 15,2),
            new Vertex(255, 33, 16,2),
            new Vertex(211, 202, 17,2),
            new Vertex(240, 11, 18,2),
            new Vertex(153, 170, 19,2),
    };

    private static final Vertex[] player1 = {
            new Vertex(134, 44, 0, 1),
            new Vertex(306, 364, 1, 1),
            new Vertex(31, 147, 2, 1),
            new Vertex(295, 246, 3, 1),
            new Vertex(89, 354, 4, 1),
            new Vertex(394, 111, 5, 1),
            new Vertex(203, 389, 6, 1),
            new Vertex(373, 334, 7, 1),
            new Vertex(264, 10, 8, 1),
            new Vertex(352, 152, 9, 1),
            new Vertex(22, 204, 10, 1),
            new Vertex(44, 6, 11, 1),
            new Vertex(219, 176, 12, 1),
            new Vertex(92, 4, 13, 1),
            new Vertex(272, 11, 14, 1),
            new Vertex(163, 120, 15, 1),
            new Vertex(183, 14, 16, 1),
            new Vertex(168, 173, 17, 1),
            new Vertex(265, 383, 18, 1),
            new Vertex(6, 284, 19, 1),
    };

    private static final Vertex[] player2 = {
            new Vertex(326, 235, 20, 2),
            new Vertex(397, 340, 21, 2),
            new Vertex(6, 322, 22, 2),
            new Vertex(61, 322, 23, 2),
            new Vertex(309, 201, 24, 2),
            new Vertex(9, 71, 25, 2),
            new Vertex(41, 37, 26, 2),
            new Vertex(89, 45, 27, 2),
            new Vertex(96, 389, 28, 2),
            new Vertex(314, 156, 29, 2),
            new Vertex(46, 163, 30, 2),
            new Vertex(164, 172, 31, 2),
            new Vertex(49, 5, 32, 2),
            new Vertex(197, 249, 33, 2),
            new Vertex(226, 35, 34, 2),
    };

    private static final Vertex[] player2_new = {
            new Vertex(294.000000, 21.000000, 20, 2),
            new Vertex(303.000000, 351.000000, 21, 2),
            new Vertex(183.000000, 81.000000, 22, 2),
            new Vertex(74.000000, 176.000000, 23, 2),
            new Vertex(304.000000, 245.000000, 24, 2),
            new Vertex(137.000000, 344.000000, 25, 2),
            new Vertex(323.000000, 375.000000, 26, 2),
            new Vertex(87.000000, 376.000000, 27, 2),
            new Vertex(43.000000, 118.000000, 28, 2),
            new Vertex(123.000000, 228.000000, 29, 2),
            new Vertex(393.000000, 90.000000, 30, 2),
            new Vertex(291.000000, 247.000000, 31, 2),
            new Vertex(156.000000, 180.000000, 32, 2),
            new Vertex(41.000000, 224.000000, 33, 2),
            new Vertex(252.000000, 159.000000, 34, 2),
    };

    private static final Vertex[] StealLargestFaceLost = {
            new Vertex(314.000000, 107.000000, 0, 1),
            new Vertex(90.000000, 314.000000, 1, 1),
            new Vertex(351.000000, 176.000000, 2, 1),
            new Vertex(229.000000, 304.000000, 3, 1),
            new Vertex(360.000000, 230.000000, 4, 1),
            new Vertex(276.000000, 327.000000, 5, 1),
            new Vertex(125.000000, 288.000000, 6, 1),
            new Vertex(263.000000, 164.000000, 7, 1),
            new Vertex(231.000000, 340.000000, 8, 1),
            new Vertex(183.000000, 52.000000, 9, 1),
            new Vertex(48.000000, 7.000000, 10, 1),
            new Vertex(39.000000, 153.000000, 11, 1),
            new Vertex(186.000000, 104.000000, 12, 1),
            new Vertex(58.000000, 366.000000, 13, 1),
            new Vertex(104.000000, 146.000000, 14, 1),
            new Vertex(241.000000, 219.000000, 15, 1),
            new Vertex(349.000000, 309.000000, 16, 1),
            new Vertex(47.000000, 244.000000, 17, 1),
            new Vertex(223.000000, 14.000000, 18, 1),
            new Vertex(91.000000, 76.000000, 19, 1),
    };

    private static final Vertex[] Error = {
            new Vertex(100,100,0,1),
            new Vertex(25,225,1,2),
            new Vertex(25,25,2,2)
    };

    private static final HashMap<String, Vertex[]> exampleMap = new HashMap<>();

    private Vertex[] exampleSet;

    static {
        exampleMap.put("uniform", uniform);
        exampleMap.put("uniform2", uniform2);
        exampleMap.put("badcase", badcase);
        exampleMap.put("badcase2", badcase2);
        exampleMap.put("badcase3", badcase3);
        exampleMap.put("fixed", fixed);
        exampleMap.put("player1", player1);
        exampleMap.put("player2", player2);
        exampleMap.put("player2_new", player2_new);
        exampleMap.put("error", Error);
        exampleMap.put("StealLargestFaceLost", StealLargestFaceLost);
    }

    public ExampleInputGenerator(String exampleName){
        exampleSet = exampleMap.get(exampleName);
    }

    @Override
    public Vertex[] generate() {
        return exampleSet;
    }


}
