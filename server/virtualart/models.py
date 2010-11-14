from django.db import models
from django.contrib.auth.models import User
# Create your models here.


class Art(models.Model):
    class Meta: app_label = "virtualart"
    
    name = models.CharField(max_length=100)
    image = models.ImageField(upload_to="imgs/%Y/%m/%d")
    longitude = models.FloatField()
    latitude = models.FloatField()
    direction = models.FloatField()
    pitch = models.FloatField()
    elevation = models.FloatField()
    #space = IntegerField(default=0, db_index=True)
    #zone = IntegerField(default=0, db_index=True)
    #user = models.ForeignKey(User)

    def __unicode__(self):
        return "<Art: %s>" % self.name

    def get_data(self, host=""):
        return {
            "id": self.id,
            "name": self.name,
            "image": "http://" + host + self.image.url,
            "longitude": self.longitude,
            "latitude": self.latitude,
            "direction": self.direction,
            "pitch": self.pitch,
            "elevation": self.elevation,            
        }
