# Generated by Django 2.2.6 on 2019-12-29 13:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('clothes', '0008_auto_20191224_1325'),
    ]

    operations = [
        migrations.AddField(
            model_name='clothing',
            name='img',
            field=models.ImageField(null=True, upload_to=''),
        ),
    ]
