<?php
include 'api/api.php';
include 'templates/header.php';
$board = isset($_GET['board']) ? $_GET['board'] : 'playtime';
$score = new SScoreboad('config/scoreboards.json',$board);
?>
<div class="span8 offset2" style="background-color: #FAFAFA">
<style>
.head{
width:32px;
height:32px;
}
</style>
<h1><?php echo $score->the_title(); ?></h1>
<table class="table">
<tr><th></th><th>Rank</th><th>Player</th>
<?php 
while($score->have_field()){
 echo "<th>" . $score->the_field_name() . "</th>";
}
$score->reset_field();
?></tr>
</tr>
<?php 
while($score->have_entry()){
?><tr><td><canvas class="head" data-name="<?php echo $score->the_player_name(); ?>"></canvas></td><td><?php echo $score->the_rank(); ?></td><td><?php echo $score->the_player_name(); ?></td><?php 
 while($score->have_field()){
  echo "<td class=\"" . $score->the_field_name() . "\">" . $score->the_field_value() . "</td>"; 
 } 
 echo "</tr>";
 $score->reset_field();
}
?>
</table>
<script type="text/javascript" src="js/PlayerHead.js"></script>
</div>
<?php include 'templates/footer.php';?>